package kr.ryan.alarm.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.viewmodel.AlarmViewModel

/**
 * Alarm
 * Class: AlarmViewModelFactory
 * Created by pyg10.
 * Created On 2021-08-06.
 * Description:
 */
class AlarmViewModelFactory(private val repository: AlarmRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java))
            return AlarmViewModel(repository) as T
        throw IllegalStateException("unKnown ViewModel Class")
    }
}