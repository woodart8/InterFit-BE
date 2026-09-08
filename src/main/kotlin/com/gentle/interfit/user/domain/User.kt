package com.gentle.interfit.user.domain

class User(
    val id: Long? = null,
    var name: String,
    val email: String,
    val password: String
) {

    fun updateName(name: String) {
        this.name = name
    }
}