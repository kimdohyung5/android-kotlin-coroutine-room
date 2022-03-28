package com.kimdo.roomdbtest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    val username: String,

    @ColumnInfo(name="password_hash")
    val passwordHash: Int,

    val info: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}