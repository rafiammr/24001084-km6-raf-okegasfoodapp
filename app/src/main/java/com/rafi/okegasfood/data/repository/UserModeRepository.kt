package com.rafi.okegasfood.data.repository

import com.rafi.okegasfood.data.datasource.user.UserDataSource

interface UserModeRepository {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserModeRepositoryImpl(private val dataSource: UserDataSource) : UserModeRepository{
    override fun isUsingGridMode(): Boolean {
        return dataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        return dataSource.setUsingGridMode(isUsingGridMode)
    }

}