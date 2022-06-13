package com.example.curiosity

data class User(
    var userEmail: String? = null,
    var userPsw: String? = null,
    var key : String
){

    constructor() : this("","","")

    override fun toString(): String {
        return userEmail.toString()
    }

    fun set(item : User){
        userEmail = item.userEmail
        userPsw = item.userPsw
        key = item?.key.toString()
    }
}