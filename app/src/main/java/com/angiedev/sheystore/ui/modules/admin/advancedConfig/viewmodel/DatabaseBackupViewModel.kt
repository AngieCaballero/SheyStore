package com.angiedev.sheystore.ui.modules.admin.advancedConfig.viewmodel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.repository.backup.IBackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class DatabaseBackupViewModel @Inject constructor(
    private val backupRepository: IBackupRepository
) : ViewModel() {

    private val _downloadFile = MutableLiveData<String>()
    val downloadFile get() = _downloadFile

    fun downloadDatabaseBackup() {
        runBlocking(Dispatchers.IO) {
            val response = backupRepository.downloadBackup()
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/DumpDatabase.sql")
                val isSaved = saveFile(response, file)
                _downloadFile.postValue(isSaved)
            } else {
                _downloadFile.postValue("Failed to download file")
            }
        }
    }

    private fun saveFile(response: ResponseBody, filePath: File) : String {
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null

        try {
            inputStream = response.byteStream()
            outputStream = FileOutputStream(filePath)
            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.flush()
            return "File downloaded successfully!"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Failed to download file"
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }
}