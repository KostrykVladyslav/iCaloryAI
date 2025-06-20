package com.kostryk.icaloryai.domain.database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.database.entity.DishDatabaseEntity

@ConstructedBy(AppDatabaseConstructor::class)
@Database(entities = [DishDatabaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDishDao(): DishDatabaseDao
}