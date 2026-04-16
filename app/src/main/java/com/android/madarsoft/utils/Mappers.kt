package com.android.madarsoft.utils

import com.android.madarsoft.data.local.entity.UserEntity
import com.android.madarsoft.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        age = age,
        jobTitle = jobTitle,
        gender = gender
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        age = age,
        jobTitle = jobTitle,
        gender = gender
    )
}
