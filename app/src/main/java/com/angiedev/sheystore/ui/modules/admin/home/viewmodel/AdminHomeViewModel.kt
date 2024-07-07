package com.angiedev.sheystore.ui.modules.admin.home.viewmodel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.userCount.UserCountEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val reportRepository: IReportRepository
) : ViewModel() {

    private val _productSoldGlobalQuantity =
        MutableLiveData<ApiResponse<List<ProductSoldQuantityEntity>>>()
    val productSoldGlobalQuantity get() = _productSoldGlobalQuantity

    private val _usersReport = MutableLiveData<ApiResponse<List<UserCountEntity>>>()
    val usersReport get() = _usersReport

    private val _downloadFile = MutableLiveData<String>()
    val downloadFile get() = _downloadFile

    fun getProductSoldGlobalQuantity() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getProductsSoldGlobalQuantity()
            _productSoldGlobalQuantity.postValue(response)
        }
    }

    fun getUsersReport() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getUsersReport()
            _usersReport.postValue(response)
        }
    }

    fun downloadUsersReport() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.downloadUsersReport()
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/UsersReport.xlsx")
                val isSaved = saveFile(response, file)
                _downloadFile.postValue(isSaved)
            } else {
                _downloadFile.postValue("Failed to download file")
            }
        }
    }

    fun downloadProductSoldGlobalReport() {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.downloadProductSoldGlobalQuantity()
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/ProductSoldGlobalReport.xlsx")
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