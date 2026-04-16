package com.android.madarsoft.presentation.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel @Inject constructor(
    getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    val users: StateFlow<List<User>> = getAllUsersUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
