package com.alextos.cashback.core.domain.services

interface UserDataService {
    var delegate: UserDataDelegate?
    var provider: UserDataFileProvider?

    fun initiateImport()
    suspend fun continueImport(json: String)
    suspend fun exportData()
}

interface UserDataDelegate {
    fun userDataServiceDidFinishImport()
}

interface UserDataFileProvider {
    fun showFilePicker()
}