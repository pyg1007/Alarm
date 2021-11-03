package kr.ryan.weatheralarm.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.ryan.weatheralarm.room.AlarmDao
import kr.ryan.weatheralarm.room.AlarmDatabase
import javax.inject.Singleton

/**
 * WeatherAlarm
 * Class: DataBaseModule
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AlarmDatabase {
        return AlarmDatabase.getInstance(context)
    }

    @Provides
    fun provideDao(alarmDatabase: AlarmDatabase): AlarmDao {
        return alarmDatabase.alarmDao()
    }

}