package com.android.madarsoft.domain.usecase

import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.repository.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setup() {
        userRepository = mock()
        addUserUseCase = AddUserUseCase(userRepository)
    }

    @Test
    fun `valid user calls repo addUser`() = runTest {
        // Arrange
        val user = User(name = "Ahmed", age = 25, jobTitle = "Developer", gender = "Male")

        // Act
        addUserUseCase(user)

        // Assert
        verify(userRepository).addUser(user)
    }

    @Test
    fun `empty name throws IllegalArgumentException`() {
        // Arrange
        val user = User(name = "", age = 25, jobTitle = "Developer", gender = "Male")

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java) {
            kotlinx.coroutines.runBlocking {
                addUserUseCase(user)
            }
        }
    }

    @Test
    fun `negative age throws IllegalArgumentException`() {
        // Arrange
        val user = User(name = "Ahmed", age = -5, jobTitle = "Developer", gender = "Male")

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java) {
            kotlinx.coroutines.runBlocking {
                addUserUseCase(user)
            }
        }
    }
}
