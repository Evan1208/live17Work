package com.example.live17work.roomData

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users WHERE login LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, User>

    @Query("DELETE FROM users")
    suspend fun clearAll()
}