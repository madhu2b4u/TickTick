package com.demo.ticktick.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDao {

    @Query("SELECT * FROM db_ticktick ORDER BY timestamp DESC")
    abstract fun getAllTodosFromDatabase(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveTodoToDatabase(todo: TodoEntity)

    @Update
    abstract fun updateTodo(todo: TodoEntity)

    @Delete
    abstract fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM db_ticktick WHERE id = :id")
    abstract fun getTodoById(id: Int): TodoEntity?

    @Query("SELECT * FROM db_ticktick WHERE status = :status ORDER BY timestamp DESC")
    abstract fun getTodosByStatus(status: Boolean): Flow<List<TodoEntity>>
}
