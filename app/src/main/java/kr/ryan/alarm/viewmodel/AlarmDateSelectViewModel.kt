package kr.ryan.alarm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val _add = MutableLiveData(false)
    val add: LiveData<Boolean>
        get() = _add

    private val _cancel = MutableLiveData(false)
    val cancel: LiveData<Boolean>
        get() = _cancel

    val calendarDateClick = fun(date: Date) {
        _selectDate = date
    }

    fun addBtnClick(){
        _add.value = true
    }

    fun clearAddBtnStatus(){
        _add.value = false
    }

    fun cancelBtnClick(){
        _cancel.value = true
    }

    fun clearCancelBtnStatus(){
        _cancel.value = false
    }

}