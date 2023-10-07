package com.example.jubloodbank.bloodlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BloodEntity(
    val Name:String?=null,
    val phoneNumber:String?=null,
    val HallName:String?=null,
    val bloodGroup:String?=null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0


)
