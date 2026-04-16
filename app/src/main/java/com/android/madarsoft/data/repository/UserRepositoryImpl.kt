package com.android.madarsoft.data.repository

import com.android.madarsoft.data.local.dao.UserDao
import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.repository.UserRepository
import com.android.madarsoft.utils.toDomain
import com.android.madarsoft.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun addUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
