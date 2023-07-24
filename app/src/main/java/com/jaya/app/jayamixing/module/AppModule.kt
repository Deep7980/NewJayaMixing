package com.jaya.app.jayamixing.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jaya.app.core.common.EmitType
import com.jaya.app.core.common.MetarFields
import com.jaya.app.core.domain.repositories.AddProductRepository
import com.jaya.app.core.domain.repositories.DashboardRepository
import com.jaya.app.core.domain.repositories.LoginRepository
import com.jaya.app.core.domain.repositories.OtpRepository
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.helpers.Info
import com.jaya.app.jayamixing.application.JayaMixingApp
import com.jaya.app.jayamixing.helpers_impl.AppInfo
import com.jaya.app.jayamixing.helpers_impl.AppStoreImpl
import com.jaya.app.jayamixing.repository_impl.AddProductRepositoryImpl
import com.jaya.app.jayamixing.repository_impl.DashboardRepositoryImpl
import com.jaya.app.jayamixing.repository_impl.LoginRepositoryImpl
import com.jaya.app.jayamixing.repository_impl.OtpRepositoryImpl
import com.jaya.app.jayamixing.repository_impl.SplashRepositoryImpl
import com.jaya.app.jayamixing.utility.Constants
import com.jaya.app.jayamixing.utility.Metar
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object{
        private val Context.dataStore by preferencesDataStore("jayaMixing")

        private fun <T> provideApi(klass: Class<T>): T {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(
                ChuckerInterceptor.Builder(JayaMixingApp.app!!.applicationContext)
                    .collector(ChuckerCollector(JayaMixingApp.app!!.applicationContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            ).build()

            return Retrofit.Builder()
//                .baseUrl(Metar[Constants.BASE_URL])
                .baseUrl(HttpUrl
                    .Builder()
                    .scheme("https").host(Metar[MetarFields.BaseUrl()]).build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(klass)
        }

        @Singleton
        @Provides
        fun provideMyApiList(): MyApiList = provideApi(MyApiList::class.java)

        @Singleton
        @Provides
        fun provideDataStorePreferences(@ApplicationContext appContext: Context): DataStore<Preferences> = appContext.dataStore

        @Provides
        @Singleton
        fun provideGlobalCoroutineScope(): CoroutineScope =
            CoroutineScope(context = Dispatchers.Main + SupervisorJob())

    }

    @Binds
    fun bindAppStore(appStoreImpl: AppStoreImpl): AppStore
//
    @Binds
    fun bindAppInfo(appInfo: AppInfo): Info

    @Binds
    fun bindSplashRepo(impl: SplashRepositoryImpl): SplashRepository
//
    @Binds
    fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    fun bindOtpRepository(impl: OtpRepositoryImpl): OtpRepository

    @Binds
    fun bindDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository

    @Binds
    fun bindAddProductRepository(impl: AddProductRepositoryImpl): AddProductRepository
}