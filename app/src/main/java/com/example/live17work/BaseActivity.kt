package com.example.live17work

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<binding: ViewDataBinding>: AppCompatActivity() {

    protected val mBinding:binding by lazy {
        DataBindingUtil.setContentView(this, mLayoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }



    abstract val mLayoutId:Int
    abstract fun initView()

}