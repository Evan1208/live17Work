package com.example.live17work.roomData

import androidx.room.Entity

@Entity(tableName = "users")
data class User(val id: Int, val login: String, val avatar_url: String,
                val site_admin: Boolean)
//var login: String ?= null
//var id: Int = -1
//var avatar_url: String ?= null
//var site_admin: Boolean  = false