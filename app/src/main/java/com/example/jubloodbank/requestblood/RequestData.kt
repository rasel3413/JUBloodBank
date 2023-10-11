package com.example.jubloodbank.requestblood

import com.google.firebase.database.ServerValue

data class RequestData(
    var patient:String,
    var Blood:String?=null,
    var amuont:String,
    var Date:String,
    var Time:String,
    var Hname:String,
    var Location:String,

    var personName:String,
    var phone:String,
    var note:String,

    var id:String?=null,
    val createdTime: Any = ServerValue.TIMESTAMP
)
