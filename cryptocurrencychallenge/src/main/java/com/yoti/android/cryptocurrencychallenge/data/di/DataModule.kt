package com.yoti.android.cryptocurrencychallenge.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yoti.android.cryptocurrencychallenge.data.CAPCOIN_ENDPOINT_HOST
import com.yoti.android.cryptocurrencychallenge.data.CoinRepoImpl
import com.yoti.android.cryptocurrencychallenge.data.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.cache.AppDatabase
import com.yoti.android.cryptocurrencychallenge.data.cache.CoinCacheImpl
import com.yoti.android.cryptocurrencychallenge.data.remote.CoinRemoteImpl
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCache
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRemote
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideCoinRemote(api: CoincapService): CoinRemote = CoinRemoteImpl(api)

    @Singleton
    @Provides
    fun provideCoinCache(appDatabase: AppDatabase): CoinCache = CoinCacheImpl(appDatabase)

    @Provides
    @Singleton
    fun providesCoinRepository(coinCache: CoinCache, coinRemote: CoinRemote): CoinRepository {
        return CoinRepoImpl(coinCache, coinRemote)
    }


    @Provides
    @Singleton
    fun provideCoincapService(): CoincapService {
        return Retrofit.Builder()
            .baseUrl(CAPCOIN_ENDPOINT_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoincapService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "coin-base"
        ).build()
    }
}