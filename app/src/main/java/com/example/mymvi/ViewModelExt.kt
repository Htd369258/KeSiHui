package com.example.mymvi

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * ViewModel扩展方法：启动协程
 * @param block 协程逻辑
 * @param onError 错误回调方法
 * @param onComplete 完成回调方法
 */
fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = { _: Throwable -> },
    onComplete: () -> Unit = {}
) {
    viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            run {
                // 这里统一处理错误
                ExceptionUtil.catchException(throwable)
                onError(throwable)
            }
        }
    ) {
        try {
            block.invoke(this)
        } finally {
            onComplete()
        }
    }
}

/**
 * viewModel 请求装换成类似rxjava形式
 */
fun <T> BaseViewModel.reqLaunch(
        api: suspend () -> DataResult<T> ,
        result: ApiResultCallBack<T?> ,
        showLoading: Boolean = true ,
        showLoadingNow: Boolean = false ,
        isShowErrorMsg: Boolean = true
) {

//    viewModelScope.launch {
//        reqApi {
//            api.invoke()
//        }.loading(
//            showLoading,
//            showLoadingNow,
//            getUIChangeLiveDataEvent().showLoadingNowEvent,
//            getUIChangeLiveDataEvent().dismissLoadingDialogEvent
//        ).collectData(result, isShowErrorMsg)
//    }
}

/**
 * 剥离DataResult
 */
fun <T> Flow<DataResult<T>>.transformData(): Flow<T?> {
    return transform { value ->
        if (value.code == 200) {
            emit(value.results)
        } else {
            throw ApiException(value.msg, value.code)
        }
    }
}

fun <T> reqApi(api: suspend () -> DataResult<T>): Flow<T?> {
    return flow {
        emit(api())
    }.flowOn(Dispatchers.IO).transformData()
}

fun <T> Flow<T>.loading(
    show: Boolean,showLoadingNow: Boolean,
    showLoading: MutableLiveData<Boolean>?,
    dismissLoading: MutableLiveData<Boolean>?
): Flow<T> {
    return this.onStart {
        if(show)
         showLoading?.value = showLoadingNow
    }.onCompletion {
        dismissLoading?.value = true
    }
}

/**
 * 结果回调
 */
suspend fun <T> Flow<T>.collectData(result: ApiResultCallBack<T>, isShowErrorMsg: Boolean = true) {
    this.catch {
        result.onError(it)
        if (isShowErrorMsg)
            ExceptionUtil.catchException(it)
    }.collect {
        try {
            result.onSuccess(it)
        } catch (e: Exception) {
            result.onError(e)
            if (isShowErrorMsg)
                ExceptionUtil.catchException(e)
        }
    }
}
