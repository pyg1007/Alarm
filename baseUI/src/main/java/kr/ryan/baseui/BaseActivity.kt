package kr.ryan.baseui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: BaseActivity
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
abstract class BaseActivity<VDB : ViewDataBinding>(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {

    protected lateinit var binding: VDB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)
    }
}