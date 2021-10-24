package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.ryan.weatheralarm.repository.DeleteRepository
import kr.ryan.weatheralarm.repository.InsertRepository
import kr.ryan.weatheralarm.repository.SelectRepository
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmViewModel
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
@HiltViewModel
class AlarmViewModel @Inject constructor(
    selectUseCase: AlarmSelectUseCase,
    insertUseCase: AlarmInsertUseCase,
    deleteUseCase: AlarmDeleteUseCase,
    updateUseCase: AlarmUpdateUseCase
) : ViewModel() {


}