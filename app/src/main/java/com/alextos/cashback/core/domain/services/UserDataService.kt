package com.alextos.cashback.core.domain.services

import com.alextos.cashback.core.data.dto.UserData

interface UserDataService {
    suspend fun exportData(data: UserData)
}