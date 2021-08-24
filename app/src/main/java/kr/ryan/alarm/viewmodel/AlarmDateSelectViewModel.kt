package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kr.ryan.alarm.utility.dateToString
import java.util.*

/**
 * Alarm
 * Class: AlarmDateSelectViewModel
 * Created by pyg10.
 * Created On 2021-08-24.
 * Description:
 */
class AlarmDateSelectViewModel : ViewModel() {

    private var _selectDate = Date()
    val selectDate: Date
        get() = _selectDate

    val calendarDateClick = fun(date: Date) {
        _selectDate = date
        Log.e(TAG, _selectDate.dateToString("yyyy MM dd HH:mm"))
    }

    companion object{
        const val TAG = "AlarmDateSelectViewModel"
    }

}