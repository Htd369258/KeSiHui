package com.example.mymvi

data class ApiException(var msg: String, var code: Int) : RuntimeException()