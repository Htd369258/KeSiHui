package com.example.mymvi

sealed class BaseIntent {
    data class ShowLoadingNow(val now : Boolean) : BaseIntent()
    
    object DismissLoading : BaseIntent()
    
    data class ShowToastMessage(val message : String) : BaseIntent()
}