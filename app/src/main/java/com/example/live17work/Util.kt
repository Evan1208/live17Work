package com.example.live17work

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.DecimalFormat
import java.util.*

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by evan.chang on 2021/5/3.
 * #主維護
 * #副維護
 */

object Util {
    const val TYPE_LOGIN = "LOGIN"
    const val TYPE_EMPTY = "EMPTY"

    private var mRequestOptions: RequestOptions? = null

    fun getNumber(pInt: Int): String? {
        Locale.setDefault(Locale.US)
        val mDecimalFormat = DecimalFormat("#,###")
        return mDecimalFormat.format(pInt)
    }

    fun getRequestOptions(): RequestOptions {
        return mRequestOptions?.let {
            mRequestOptions
        } ?: kotlin.run {
            mRequestOptions = RequestOptions().transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .placeholder(R.drawable.ic_cat)
                .error(R.drawable.ic_cat)
                .priority(Priority.NORMAL)
            mRequestOptions!!
        }
    }

    fun errorContent(pCode: String?, pContent: String?): String {
        return "${pCode ?: "Empty!"} \n ${pContent ?: "Empty!"}"
    }
}