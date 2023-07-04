package com.jaya.app.jayamixing.utility

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivity(
        @ApplicationContext context: Context, coroutineScope: CoroutineScope
    ): AppConnectivity = AppConnectivity(context, coroutineScope)
}