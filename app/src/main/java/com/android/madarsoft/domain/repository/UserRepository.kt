package com.android.madarsoft.domain.repository

import com.android.madarsoft.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addUser(user: User)
    fun getAllUsers(): Flow<List<User>>
}
