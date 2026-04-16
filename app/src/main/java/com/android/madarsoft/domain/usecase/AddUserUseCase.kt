package com.android.madarsoft.domain.usecase

import com.android.madarsoft.domain.model.User
import com.android.madarsoft.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        require(user.name.isNotBlank()) { "Name cannot be empty" }
        require(user.age > 0) { "Age must be positive" }
        require(user.jobTitle.isNotBlank()) { "Job Title cannot be empty" }
        require(user.gender.isNotBlank()) { "Gender cannot be empty" }
        
        userRepository.addUser(user)
    }
}
