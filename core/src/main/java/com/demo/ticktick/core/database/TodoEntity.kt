package com.demo.ticktick.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_ticktick")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val status: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)