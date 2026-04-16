package com.android.madarsoft.data.repository

import com.android.madarsoft.data.local.dao.UserDao
import com.android.madarsoft.data.local.entity.UserEntity
import com.android.madarsoft.domain.model.User
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        userDao = mock()
        userRepository = UserRepositoryImpl(userDao)
    }

    @Test
    fun `insertUser delegates to DAO`() = runTest {
        // Arrange
        val user = User(id = 1, name = "Ahmed", age = 25, jobTitle = "Developer", gender = "Male")
        val entity = UserEntity(id = 1, name = "Ahmed", age = 25, jobTitle = "Developer", gender = "Male")

        // Act
        userRepository.addUser(user)

        // Assert
        verify(userDao).insertUser(entity)
    }

    @Test
    fun `getAllUsers maps entities correctly`() = runTest {
        // Arrange
        val entityList = listOf(UserEntity(id = 1, name = "Ahmed", age = 25, jobTitle = "Developer", gender = "Male"))
        whenever(userDao.getAllUsers()).thenReturn(flowOf(entityList))

        // Act
        val result = userRepository.getAllUsers().toList()

        // Assert
        assertEquals(1, result.size)
        val domainList = result[0]
        assertEquals(1, domainList.size)
        assertEquals("Ahmed", domainList[0].name)
        assertEquals(25, domainList[0].age)
    }
}
