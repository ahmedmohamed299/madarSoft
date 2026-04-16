package com.android.madarsoft.presentation.input

import app.cash.turbine.test
import com.android.madarsoft.domain.usecase.AddUserUseCase
import com.android.madarsoft.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class InputViewModelTest {

    private lateinit var addUserUseCase: AddUserUseCase
    private lateinit var viewModel: InputViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        addUserUseCase = mock()
        viewModel = InputViewModel(addUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveUser emits UiState Loading then UiState Success`() = runTest {
        // Arrange
        whenever(addUserUseCase(any())).thenReturn(Unit)

        // Act & Assert
        viewModel.uiState.test {
            assertEquals(UiState.Idle, awaitItem()) // Initial state

            viewModel.saveUser("Ahmed", "25", "Developer", "Male")

            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(Unit), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invalid input emits UiState Error`() = runTest {
        // Arrange
        whenever(addUserUseCase(any())).thenThrow(IllegalArgumentException("Invalid age"))

        // Act & Assert
        viewModel.uiState.test {
            assertEquals(UiState.Idle, awaitItem())

            viewModel.saveUser("Ahmed", "-5", "Developer", "Male")

            assertEquals(UiState.Loading, awaitItem())
            val errorState = awaitItem()
            assert(errorState is UiState.Error)
            assertEquals("Invalid age", (errorState as UiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
