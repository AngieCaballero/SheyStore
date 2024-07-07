package com.angiedev.sheystore.data.repository.backup

import okhttp3.ResponseBody

interface IBackupRepository {

    suspend fun downloadBackup(): ResponseBody?
}