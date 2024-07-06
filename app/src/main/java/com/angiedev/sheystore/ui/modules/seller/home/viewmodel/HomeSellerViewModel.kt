package com.angiedev.sheystore.ui.modules.seller.home.viewmodel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.domain.entities.report.income.IncomeEntity
import com.angiedev.sheystore.domain.entities.report.productSoldQuantity.ProductSoldQuantityEntity
import com.angiedev.sheystore.domain.entities.report.topCategories.TopCategoriesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class HomeSellerViewModel @Inject constructor (
    private val reportRepository: IReportRepository
) : ViewModel() {

    private val _topCategories = MutableLiveData<ApiResponse<List<TopCategoriesEntity>>>()
    val topCategories get() = _topCategories

    private val _income = MutableLiveData<ApiResponse<List<IncomeEntity>>>()
    val income get() = _income

    private val _productSoldQuantity = MutableLiveData<ApiResponse<List<ProductSoldQuantityEntity>>>()
    val productSoldQuantity get() = _productSoldQuantity

    private val _downloadFile = MutableLiveData<String>()
    val downloadFile get() = _downloadFile

    fun getTopCategories(userId: Int) {
         runBlocking(Dispatchers.IO) {
             val response = reportRepository.getTopCategories(userId)
             _topCategories.postValue(response)
         }
    }

    fun downloadTopCategoriesReport(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.downloadTopCategoriesReport(userId)
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/TopCategoriesReport.xlsx")
                val isSaved = saveFile(response, file)
                _downloadFile.postValue(isSaved)
            } else {
                _downloadFile.postValue("Failed to download file")
            }
        }
    }

    fun downloadProductsSoldQuantityReport(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.downloadProductsSoldQuantityReport(userId)
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/ProductsSoldQuantityReport.xlsx")
                val isSaved = saveFile(response, file)
                _downloadFile.postValue(isSaved)
            } else {
                _downloadFile.postValue("Failed to download file")
            }
        }
    }

    fun downloadIncomeReport(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.downloadIncomeReport(userId)
            if (response != null) {
                val file = File("${Environment.getExternalStorageDirectory()}/Download/IncomeReport.xlsx")
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

    fun getIncome(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getIncome(userId)
            _income.postValue(response)
        }
    }

    fun getProductSoldQuantity(userId: Int) {
        runBlocking(Dispatchers.IO) {
            val response = reportRepository.getProductSoldQuantity(userId)
            _productSoldQuantity.postValue(response)
        }
    }
}
