package com.demo.ticktick.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDao {

    @Query("SELECT * FROM db_ticktick ORDER BY timestamp DESC")
    abstract fun getAllTodosFromDatabase(): Flow<List<TodoEntity>>

    /*
    IGNORE prevents overwriting an existing record if
    there's a conflict (e.g., if an id already exists).

    Since id is auto-generated and unique, there shouldn't be conflicts unless
    explicitly specified. Using IGNORE adds an extra safeguard.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun saveTodoToDatabase(todo: TodoEntity)
}
