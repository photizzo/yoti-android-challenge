package com.yoti.android.cryptocurrencychallenge

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoti.android.cryptocurrencychallenge.data.cache.AppDatabase
import com.yoti.android.cryptocurrencychallenge.data.cache.dao.CoinDao
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class CoinCacheImplTest {
    private lateinit var coinDao: CoinDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        coinDao = db.coinDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAssetsSucceeds() = runBlocking{
        val asset = generateCacheAssetData(10)
        coinDao.insertAllAssets(asset)
        val fromCache = coinDao.getAllAssets()
        assertThat(asset.first().id, equalTo(fromCache.first().id))
    }

    @Test
    @Throws(Exception::class)
    fun insertMarketDataSucceeds() = runBlocking{
        val data = generateCacheMarketData(10)
        coinDao.insertAllMarketData(data)
        val fromCache = coinDao.getAllMarketData()
        assertThat(data.first().baseId, equalTo(fromCache.first().baseId))
    }
}