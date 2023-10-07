package com.example.jubloodbank.bloodlist.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [BloodEntity::class],
    version=1
)
abstract class BloodDatabase:RoomDatabase() {
    abstract  val bloodDao:BloodDao
}