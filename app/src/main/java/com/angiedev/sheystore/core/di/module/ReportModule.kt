package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.report.IReportRepository
import com.angiedev.sheystore.data.repository.report.ReportRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReportModule {

    @Binds
    abstract fun provideReportModule(reportRepositoryImp: ReportRepositoryImp) : IReportRepository

}