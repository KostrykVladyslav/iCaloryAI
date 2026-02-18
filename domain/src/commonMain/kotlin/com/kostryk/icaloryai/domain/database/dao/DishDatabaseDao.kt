package com.kostryk.icaloryai.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kostryk.icaloryai.domain.database.entity.DishDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDatabaseDao {

    @Insert
    suspend fun insertDish(dish: DishDatabaseEntity)

    @Query("SELECT count(*) FROM dishes")
    suspend fun count(): Int

    @Query("SELECT * FROM dishes")
    fun getAllAsFlow(): Flow<List<DishDatabaseEntity>>

    @Query("SELECT * FROM dishes WHERE id = :id")
    fun getByIdAsFlow(id: Long): Flow<DishDatabaseEntity?>

    @Update
    suspend fun updateDish(dish: DishDatabaseEntity)

}