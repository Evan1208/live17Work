package com.example.live17work.userInfo

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.live17work.BaseActivity
import com.example.live17work.R
import com.example.live17work.Util
import com.example.live17work.databinding.ActivityUserInfoBinding
import com.example.live17work.myViewModel.GithubUserModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoActivity: BaseActivity<ActivityUserInfoBinding>() {

    private val mGithubUserModel: GithubUserModel by viewModel()
    
    override val mLayoutId: Int
        get() = R.layout.activity_user_info


    private var mLogin: String ?= null


    override fun initView() {
        mBinding.mContextLL.visibility = View.GONE
        getBundle()
        setClick()
        setObserve()
        getGitHubData()
    }

    private fun getGitHubData() {
        val (iCheckLogin, iLogin)  = mGithubUserModel.checkLoginData(mLogin)
        if( iCheckLogin) {
            mBinding.mShowProgress.visibility = View.VISIBLE
            mGithubUserModel.getUserData(iLogin)
        } else {
            Toast.makeText(this, "User 資料有錯誤!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getBundle() {
        mLogin = intent.extras?.getString(Util.TYPE_LOGIN, null)
    }

    private fun setClick() {
        mBinding.mVClose.setOnClickListener {
            this.finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserve() {
        mGithubUserModel.getUserDataLive()?.observe(this, {
            it?.let { itUserData ->
                itUserData.mErrorCode?.let { itErrorCode ->
                    mBinding.mErrorTV.text = Util.errorContent("$itErrorCode", itUserData.mErrorMessage)
                    mBinding.mErrorTV.visibility = View.VISIBLE
                } ?: kotlin.run {
                    mBinding.mVUserNameC.text = itUserData.login ?: Util.TYPE_EMPTY
                    mBinding.mVAddress.text = itUserData.location ?: Util.TYPE_EMPTY
                    mBinding.mVLink.text = itUserData.blog ?: Util.TYPE_EMPTY
                    mBinding.mName.text = itUserData.name ?: Util.TYPE_EMPTY

                    Glide.with(this)
                        .load(itUserData.avatar_url)
                        .apply(Util.getRequestOptions())
                        .into(mBinding.mVImageView)
                }
            }
            mBinding.mContextLL.visibility = View.VISIBLE
            mBinding.mShowProgress.visibility = View.GONE
        })
    }


}