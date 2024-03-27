package com.rafi.okegasfood.data.model

import java.util.logging.LogManager

data class Profile(
    val name: String,
    val username: String,
    val email: String,
    val noTelephone: Long,
    val profileImg: String
)