package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.backup.BackupRepositoryImp
import com.angiedev.sheystore.data.repository.backup.IBackupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BackupModule {

    @Binds
    abstract fun provideBackupRepository(backupRepositoryImp: BackupRepositoryImp): IBackupRepository
}