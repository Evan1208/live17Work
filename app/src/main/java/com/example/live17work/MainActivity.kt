package com.example.live17work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.live17work.adapter.UsersListAdapter
import com.example.live17work.api.GetUsersListData
import com.example.live17work.databinding.ActivityMainBinding
import com.example.live17work.myViewModel.GithubListModel
import com.example.live17work.userInfo.UserInfoActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mGithubListModel: GithubListModel by viewModel()

    private val mUsersListAdapter: UsersListAdapter by lazy {
        UsersListAdapter(this, UserComparator)
    }

    object UserComparator : DiffUtil.ItemCallback<GetUsersListData>() {
        override fun areItemsTheSame(oldItem: GetUsersListData, newItem: GetUsersListData): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: GetUsersListData, newItem: GetUsersListData): Boolean {
            return oldItem == newItem
        }
    }

    override val mLayoutId: Int
        get() = R.layout.activity_main

    override fun initView() {

        mBinding.lifecycleOwner = this
        mBinding.mVRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.mVRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mBinding.mVRecyclerView.adapter = mUsersListAdapter

        mBinding.mUserNameET.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val iName = mBinding.mUserNameET.text.toString()
                mGithubListModel.checkUserName(iName)?.let {
                    mBinding.mUserNameET.setText("")
                    val iIntent = Intent(this, UserInfoActivity::class.java)
                    val iBundle = Bundle()
                    iBundle.putString(Util.TYPE_LOGIN, it)
                    iIntent.putExtras(iBundle)
                    startActivity(iIntent)
                } ?: kotlin.run {
                    Toast.makeText(this, "請輸入使用者名稱！", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        setObserve()
    }

    private fun setObserve() {
        lifecycleScope.launch {
            mGithubListModel.mFlow.collectLatest { pagingData ->
                mUsersListAdapter.submitData(pagingData)
            }
        }

        mUsersListAdapter.addLoadStateListener { itLoadState ->
            if (itLoadState.refresh is LoadState.Loading) {
                mBinding.mErrorTV.visibility = View.GONE
                mBinding.mShowProgress.visibility = View.VISIBLE
            } else {
                mBinding.mErrorTV.visibility = View.VISIBLE
                mBinding.mShowProgress.visibility = View.GONE
                // getting the error
                val iError = when {
                    itLoadState.prepend is LoadState.Error -> itLoadState.prepend as LoadState.Error
                    itLoadState.append is LoadState.Error -> itLoadState.append as LoadState.Error
                    itLoadState.refresh is LoadState.Error -> itLoadState.refresh as LoadState.Error
                    else -> null
                }
                iError?.let {
                    val iDetail = Util.errorContent(it.error.message, (it.error as? HttpException)?.response()?.errorBody()?.string())
                    mBinding.mErrorTV.text = iDetail
                }
            }
        }
    }

}