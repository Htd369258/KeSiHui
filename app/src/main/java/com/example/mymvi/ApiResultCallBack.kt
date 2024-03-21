package com.example.mymvi

abstract class ApiResultCallBack<T> {

    abstract fun onSuccess(t: T)

    open fun onError(e: Throwable) {}
}