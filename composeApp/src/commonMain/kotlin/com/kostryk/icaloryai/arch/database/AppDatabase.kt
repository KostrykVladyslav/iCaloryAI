package com.kostryk.icaloryai.arch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kostryk.icaloryai.domain.database.dao.DishDatabaseDao
import com.kostryk.icaloryai.domain.database.entity.DishDatabaseEntity

@Database(entities = [DishDatabaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): DishDatabaseDao
}