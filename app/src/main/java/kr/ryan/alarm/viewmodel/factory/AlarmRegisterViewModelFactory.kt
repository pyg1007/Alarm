package kr.ryan.alarm.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.viewmodel.AlarmRegisterViewModel

/**
 * Alarm
 * Class: AlarmRegisterViewModelFactory
 * Created by pyg10.
 * Created On 2021-08-17.
 * Description:
 */
class AlarmRegisterViewModelFactory(private val repository: AlarmRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmRegisterViewModel::class.java))
            return AlarmRegisterViewModel(repository) as T
        throw IllegalStateException("Unknown ViewModel Class")
    }
}