package kr.ryan.baseui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * My Application
 * Class: BaseFragment
 * Created by pyg10.
 * Created On 2021-06-09.
 * Description:
 */
abstract class BaseFragment<VDB: ViewDataBinding>(@LayoutRes private val layoutRes: Int): Fragment() {

    private var _binding: VDB? = null
    protected val binding: VDB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}