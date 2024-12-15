package com.demo.ticktick.core.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TodoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "ticktick.db"

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(@NonNull context: Context): TodoDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            TodoDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun todoDao(): TodoDao

}