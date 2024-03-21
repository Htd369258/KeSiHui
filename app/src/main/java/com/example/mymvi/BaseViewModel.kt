package com.example.mymvi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(application : Application) : AndroidViewModel(application) {
    
    private val _chanel = Channel<BaseIntent>()
    
    private val _uiState = MutableStateFlow<BaseUIState>(BaseUIState.NormalState)
    
    val uiState : StateFlow<BaseUIState> = _uiState
    
    init {
        viewModelScope.launch {
            _chanel.consumeAsFlow().collect {
                when (it) {
                    is BaseIntent.ShowLoadingNow   -> {
                        _uiState.value = BaseUIState.ShowLoadingNow(it.now)
                    }
                    
                    is BaseIntent.DismissLoading   -> {
                        _uiState.value = BaseUIState.DismissLoading
                    }
                    
                    is BaseIntent.ShowToastMessage -> {
                        _uiState.value = BaseUIState.ShowToastMessage(it.message)
                    }
                    
                    else                           -> {
                        onIntentListener(it)
                    }
                }
                
            }
        }
    }
    
    /**
     * 监听意图
     */
    abstract suspend fun onIntentListener(intent : BaseIntent)
    
    /**
     * 发送意图
     */
    internal fun sendIntent(intent : BaseIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            _chanel.send(intent)
        }
    }
    
    /**
     * 发送状态
     */
    fun emitUIState(state : BaseUIState) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(state)
        }
    }
    
    /**
     * 发送Toast
     */
    fun emitToastState(message : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val toastState = BaseUIState.ShowToastMessage(message)
            _uiState.value = toastState
        }
    }
    
    /**
     * 展示loading
     */
    fun emitShowLoadingNowState(now : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val toastState = BaseUIState.ShowLoadingNow(now)
            _uiState.value = toastState
        }
    }
    
    /**
     * 关闭loading
     */
    fun emitDismissLoadingState() {
        viewModelScope.launch(Dispatchers.IO) {
            val toastState = BaseUIState.DismissLoading
            _uiState.value = toastState
        }
    }
}