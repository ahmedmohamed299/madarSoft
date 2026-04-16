package com.android.madarsoft.presentation.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.usecase.AddUserUseCase
import com.android.madarsoft.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    fun saveUser(name: String, ageStr: String, jobTitle: String, gender: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val age = ageStr.toIntOrNull() ?: throw IllegalArgumentException("Age must be a valid number")
                val user = User(
                    name = name,
                    age = age,
                    jobTitle = jobTitle,
                    gender = gender
                )
                addUserUseCase(user)
                _uiState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
