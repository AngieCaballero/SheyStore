package com.angiedev.sheystore.data.repository.backup

import com.angiedev.sheystore.data.datasource.remote.ApiDownload
import javax.inject.Inject

class BackupRepositoryImp @Inject constructor(
    private val download: ApiDownload
) : IBackupRepository {

    override suspend fun downloadBackup() = download.downloadBackup()
}