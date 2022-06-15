package com.example.curiosity

data class User(
    var userEmail: String? = null,
    var userPsw: String? = null,
    var totalAnswer: Int = 0,
    var correctAnswer: Int = 0,
    var tempoMinutiNotifica: Int = 0,
var aree_interesse : String? = null
){

    constructor() : this("",
                          "",
                        0,
                        0,
                        0)

    override fun toString(): String {
        return userEmail.toString()
    }

    fun set(item : User){
        userEmail = item.userEmail
        userPsw = item.userPsw
        totalAnswer = item?.totalAnswer
        correctAnswer = item?.correctAnswer
        tempoMinutiNotifica = item?.tempoMinutiNotifica
        aree_interesse = item?.aree_interesse

    }
}