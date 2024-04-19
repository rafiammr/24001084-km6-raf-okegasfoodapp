package com.rafi.okegasfood.data.datasource.user

import com.rafi.okegasfood.data.source.local.pref.UserPreference

interface UserDataSource {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}
class UserDataSourceImpl(private val userPreference: UserPreference): UserDataSource {
    override fun isUsingGridMode(): Boolean {
        return userPreference.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        userPreference.setUsingGridMode(isUsingGridMode)
    }
}