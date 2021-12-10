package kr.ryan.baseui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

/**
 * WeatherAlarm
 * Class: BaseDialogFragment
 * Created by pyg10.
 * Created On 2021-11-18.
 * Description:
 */
abstract class BaseDialogFragment<VDB: ViewDataBinding>(@LayoutRes private val layoutRes: Int) : DialogFragment(){

    private var _binding: VDB? = null
    protected val binding: VDB
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}