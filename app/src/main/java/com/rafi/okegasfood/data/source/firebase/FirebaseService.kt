package com.rafi.okegasfood.data.source.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

interface FirebaseService {
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

    fun getCurrentUser(): FirebaseUser?
}

class FirebaseServiceImpl(private val firebaseAuth: FirebaseAuth) : FirebaseService {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        val loginResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return loginResult.user != null
    }

    override suspend fun doRegister(
        email: String,
        fullname: String,
        password: String,
    ): Boolean {
        val registerResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        registerResult.user?.updateProfile(
            userProfileChangeRequest {
                displayName = fullname
            },
        )?.await()
        return registerResult.user != null
    }

    override suspend fun updateProfile(fullname: String?): Boolean {
        getCurrentUser()?.updateProfile(
            userProfileChangeRequest {
                fullname?.let { displayName = fullname }
            },
        )?.await()
        return true
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        getCurrentUser()?.updatePassword(newPassword)?.await()
        return true
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        getCurrentUser()?.verifyBeforeUpdateEmail(newEmail)?.await()
        return true
    }

    override suspend fun requestChangePasswordByEmail(): Boolean {
        getCurrentUser()?.email?.let {
            firebaseAuth.sendPasswordResetEmail(it)
        }
        return true
    }

    override fun doLogout(): Boolean {
        Firebase.auth.signOut()
        return true
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}
