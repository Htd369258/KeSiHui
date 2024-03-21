package com.example.mymvi

/**
 * 请求结果类，保存返回数据
 *
 *
 */
class DataResult<T>(//请求返回的数据

    var results: T?
) {
    var msg : String = "Request succeeded"
    var code:Int=0



    override fun toString(): String {
        return "DataResult{" +
                ", msg='" + msg + '\'' +
                ", code=" + code +'\'' +
                ", results=" + results +
                '}'
    }

}