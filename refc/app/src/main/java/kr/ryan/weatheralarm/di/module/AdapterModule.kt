package kr.ryan.weatheralarm.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ryan.weatheralarm.adapter.AlarmAdapter
import javax.inject.Singleton

/**
 * WeatherAlarm
 * Class: AdapterModule
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Provides
    @Singleton
    fun provideAdapter(): AlarmAdapter = AlarmAdapter()

}