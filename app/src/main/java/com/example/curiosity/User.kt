package com.example.curiosity

data class User(
    var userEmail: String? = null,
    var userPsw: String? = null,
    var totalAnswer: String? = null,
    var correctAnswer: String? = null,
    var tempoMinutiNotifica: String? = null,
var aree_interesse : String? = null
){

    constructor() : this("",
                          "",
                        "",
                        "",
                        "")

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