package kr.ryan.weatheralarm.di

import android.app.Activity
import androidx.databinding.ViewDataBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kr.ryan.baseui.BaseActivity
import javax.inject.Singleton

/**
 * WeatherAlarm
 * Class: BaseUiModule
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
//@Module
//@InstallIn(ActivityComponent::class)
//object BaseUiModule {
//
//    @Provides
//    @Singleton
//    fun provideBaseActivity(VDB: ViewDataBinding, activity: Activity) {
//        check(activity as BaseActivity<VDB>())
//    }
//
//}