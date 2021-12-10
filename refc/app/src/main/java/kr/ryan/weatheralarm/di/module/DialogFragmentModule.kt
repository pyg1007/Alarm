package kr.ryan.weatheralarm.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ryan.weatheralarm.future.dialog.AlarmDialogFragment
import javax.inject.Singleton

/**
 * WeatherAlarm
 * Class: DialogFragmentModule
 * Created by pyg10.
 * Created On 2021-12-10.
 * Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object DialogFragmentModule {

    @Singleton
    @Provides
    fun provideDialogFragment() = AlarmDialogFragment()
}