package com.android.madarsoft.domain.usecase

import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> = userRepository.getAllUsers()

}
