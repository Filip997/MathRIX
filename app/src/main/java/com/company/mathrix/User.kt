package com.company.mathrix

import java.io.Serializable

class User(val userName: String, val userAvatar: Int, val userScore: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        val otherUserName: String = (other as User).userName
        val otherUserAvatar: Int = (other as User).userAvatar

        return this.userName == otherUserName && this.userAvatar == otherUserAvatar
    }
}