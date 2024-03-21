package com.example.mymvi

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


abstract class BaseActivity<VDB:ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() {
    
    abstract fun onBindViewModel() : Class<VM>
    
    open val viewModel : VM by lazy {
        ViewModelProvider(this)[onBindViewModel()]
    }
    
    protected lateinit var viewDataBinding :VDB
    
    @LayoutRes
    protected abstract fun getLayoutResID() : Int
    
    val TAG="Main"
    
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this , getLayoutResID())
        initView(savedInstanceState)
        onClickListener()
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is BaseUIState.ShowToastMessage -> {
                        Log.d(TAG , "ShowToastMessage: ----")
                    }
                
                    is BaseUIState.ShowLoadingNow   -> {
                        Log.d(TAG , "ShowLoadingNow: ----")
                    }
                
                    is BaseUIState.DismissLoading   -> {
                        Log.d(TAG , "DismissLoading: ----")
                    }
                
                    else                            -> {
                        onUIStateListener(it)
                    }
                }
            }
        }
        
    }
    open fun onClickListener(){}
    
    open abstract fun initView(savedInstanceState : Bundle?)
    
    open abstract fun onUIStateListener(baseUIState : BaseUIState)
}