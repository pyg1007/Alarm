package kr.ryan.alarm.application

import android.app.Application
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.room.ApplicationDatabase

/**
 * Alarm
 * Class: AlarmApplication
 * Created by pyg10.
 * Created On 2021-08-06.
 * Description:
 */
class AlarmApplication : Application(){

    private val database by lazy {
        ApplicationDatabase.getInstance(this)
    }

    val repository by lazy {
        AlarmRepository(database)
    }

}