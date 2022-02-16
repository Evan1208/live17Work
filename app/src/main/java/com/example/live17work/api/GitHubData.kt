package com.example.live17work.api

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

class GetUsersListData {
    var login: String ?= null
    var id: Int = -1
    var avatar_url: String ?= null
    var site_admin: Boolean  = false
}

class GetUserData {
    var avatar_url: String ?= null
    var name: String ?= null
    var bio: String ?= null
    var login: String ?= null
    var site_admin: Boolean  = false
    var location: String ?= null
    var blog: String ?= null
    var email: String ?= null
    var followers: Int = 0
    var following: Int = 0

    var mErrorCode: Int ?= null
    var mErrorMessage: String ?= null
}