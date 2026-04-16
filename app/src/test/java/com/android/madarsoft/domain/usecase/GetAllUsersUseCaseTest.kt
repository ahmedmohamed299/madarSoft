package com.android.madarsoft.domain.usecase

import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.repository.UserRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllUsersUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Before
    fun setup() {
        userRepository = mock()
        getAllUsersUseCase = GetAllUsersUseCase(userRepository)
    }

    @Test
    fun `emits mapped list from repo`() = runTest {
        // Arrange
        val userList = listOf(User(id = 1, name = "Ahmed", age = 25, jobTitle = "Dev", gender = "Male"))
        whenever(userRepository.getAllUsers()).thenReturn(flowOf(userList))

        // Act
        val result = getAllUsersUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(userList, result[0])
    }

    @Test
    fun `emits empty list when repo is empty`() = runTest {
        // Arrange
        whenever(userRepository.getAllUsers()).thenReturn(flowOf(emptyList()))

        // Act
        val result = getAllUsersUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(emptyList<User>(), result[0])
    }
}
