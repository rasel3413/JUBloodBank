package com.example.jubloodbank.bloodlist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BloodDao {

    @Upsert
    suspend fun insertBloodData(bloodEntity: BloodEntity)

    @Delete
    suspend fun deleteBloodData(bloodEntity: BloodEntity)

    @Query("SELECT * FROM  bloodEntity WHERE bloodGroup=:blood ")
    fun getBloodDatabyGroup(blood:String):LiveData<List<BloodEntity>>

    @Query(" SELECT * FROM  bloodEntity ORDER BY Name ASC ")
    fun getBloodData(): LiveData<List<BloodEntity>>


}