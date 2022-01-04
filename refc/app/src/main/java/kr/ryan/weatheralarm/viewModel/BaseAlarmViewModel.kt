package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: BaseAlarmViewModel
 * Created by pyg10.
 * Created On 2022-01-04.
 * Description:
 */
abstract class BaseAlarmViewModel constructor(
    selectUseCase: AlarmSelectUseCase
) : ViewModel() {

    protected val selectAllAlarmList = flow {
        selectUseCase.selectAlarmWithDate().collect {
            Timber.d("$it")
            emit(it)
        }
    }.catch { e ->
        Timber.d("load alarm list failure -> $e")
        emit(emptyList())
    }

    override fun onCleared() {
        super.onCleared()
    }
}