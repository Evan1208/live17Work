package com.example.live17work.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.live17work.R
import com.example.live17work.Util
import com.example.live17work.api.GetUsersListData
import com.example.live17work.databinding.UserHolderBinding
import com.example.live17work.userInfo.UserInfoActivity


class UsersListAdapter(private val mContext: Context,
                       diffCallback: DiffUtil.ItemCallback<GetUsersListData>) :
    PagingDataAdapter<GetUsersListData, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val iBinding =
            DataBindingUtil.inflate<UserHolderBinding>(
                inflater, R.layout.user_holder,
                parent, false)
        return ViewHolder(iBinding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itData = getItem(position)
        (holder as? ViewHolder)?.let {
            val iIndex = itData?.id.toString() ?: "-1"
            val iLogin = itData?.login ?: ""
            it.mBinding.mNumberTv.text = iIndex
            it.mBinding.mVUserName.text = iLogin
            it.mBinding.mClick.tag = iLogin
            it.mBinding.mClick.setOnClickListener(mClickGoToUserDetail)
            itData?.avatar_url?.let {itUrl ->
                Glide.with(mContext)
                    .load(itUrl)
                    .apply(Util.getRequestOptions())
                    .placeholder(R.drawable.ic_cat)
                    .error(R.drawable.ic_cat)
                    .into(it.mBinding.mVImageView)
            }
        }
    }

    private val mClickGoToUserDetail = View.OnClickListener {
        if( it.tag != null) {
            val iIntent = Intent(mContext, UserInfoActivity::class.java)
            val iBundle = Bundle()
            iBundle.putString(Util.TYPE_LOGIN, it.tag.toString())
            iIntent.putExtras(iBundle)
            mContext.startActivity(iIntent)
        } else {
            Toast.makeText(mContext, "User 資料有誤!!", Toast.LENGTH_SHORT).show()
        }
    }

    inner class ViewHolder(val mBinding: UserHolderBinding) : RecyclerView.ViewHolder(mBinding.root)
}