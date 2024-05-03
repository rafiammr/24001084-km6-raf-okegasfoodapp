package com.rafi.okegasfood.data.datasource

import com.rafi.okegasfood.data.model.User
import com.rafi.okegasfood.data.model.toUser
import com.rafi.okegasfood.data.source.firebase.FirebaseService

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        email: String,
        fullname: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(fullname: String? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    suspend fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?
}

class FirebaseAuthDataSource(private val service: FirebaseService) : AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        email: String,
        fullname: String,
        password: String,
    ): Boolean {
        return service.doRegister(email, fullname, password)
    }

    override suspend fun updateProfile(fullname: String?): Boolean {
        return service.updateProfile(fullname)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return service.updatePassword(newPassword)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return service.updateEmail(newEmail)
    }

    override suspend fun requestChangePasswordByEmail(): Boolean {
        return service.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }
}
