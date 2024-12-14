package com.demo.ticktick.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_ticktick")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,// Start with 0, Room will auto-increment
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)