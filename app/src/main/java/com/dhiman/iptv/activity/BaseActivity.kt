package com.dhiman.iptv.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (layoutInflater: android.view.LayoutInflater) -> VB
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        if (_binding is ViewDataBinding) {
            (_binding as ViewDataBinding).lifecycleOwner = this
        }

        onActivityReady()
    }

    abstract fun onActivityReady()

    protected fun <T> collectFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect(collector)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
