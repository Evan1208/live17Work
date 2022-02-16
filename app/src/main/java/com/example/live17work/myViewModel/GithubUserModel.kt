package com.example.live17work.myViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.live17work.api.GetUserData
import com.example.live17work.api.GetUsersListData
import com.example.live17work.api.UtilApi
import com.example.live17work.pagin.GitHubItemPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubUserModel : ViewModel() {

    private val mUserData = MutableLiveData<GetUserData?>()
    private var mUsersListDataRepository: UserDataRepository ?= null

    fun getUserDataLive():MutableLiveData<GetUserData?>? {
        return if( mUserData.hasObservers() ) {
            null
        } else {
            mUserData
        }
    }

    fun getUserData(pString: String) {
        if( mUsersListDataRepository == null) {
            mUsersListDataRepository = UserDataRepository()
        }
        mUsersListDataRepository?.getUsersList(mUserData, pString)
    }

    fun checkLoginData(mLogin: String?): CheckLoginData<Boolean, String> {
        return mLogin?.let {
            CheckLoginData(true, it)
        } ?: kotlin.run {
            CheckLoginData(false, "")
        }
    }
}

data class CheckLoginData<out A, out B>(val first: A, val second: B)


internal class UserDataRepository {

    fun getUsersList(
        pUsersListData: MutableLiveData<GetUserData?>,
        pString: String
    ) {
        val iRetrofit  =  Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val iJsonPlaceHolderApi = iRetrofit.create(UtilApi::class.java)
        val iP = "https://api.github.com/users/$pString"
        Log.v("aaa","iP = $iP")
        val iAllInOneData = iJsonPlaceHolderApi.getGitHubUser(iP)
        iAllInOneData.enqueue(object : Callback<GetUserData> {
            override fun onFailure(call: Call<GetUserData>, t: Throwable) {
                pUsersListData.postValue(null)
            }

            override fun onResponse(call: Call<GetUserData>, response: Response<GetUserData>) {
                if( response.isSuccessful) {
                    response.body()?.apply {
                        pUsersListData.postValue(this)
                    }
                } else {
                    pUsersListData.postValue(GetUserData().apply {
                        mErrorCode = response.raw().code()
                        mErrorMessage = response.errorBody()?.string() ?: "Empty"
                    })
                }
            }

        })
    }
}