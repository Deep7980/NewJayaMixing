package com.jaya.app.jayamixing.module

import com.jaya.app.core.utils.AppNavigator
import com.jaya.app.jayamixing.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Singleton
    @Binds
    fun bindNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
}