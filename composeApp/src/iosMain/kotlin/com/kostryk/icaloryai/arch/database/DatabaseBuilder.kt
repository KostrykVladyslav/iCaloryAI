package com.kostryk.icaloryai.arch.database

import platform.Foundation.NSHomeDirectory
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/icaloryai_main.db"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}