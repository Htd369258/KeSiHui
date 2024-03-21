package com.example.mymvi

sealed class BaseUIState {
    object NormalState : BaseUIState()
    
    data class ShowLoadingNow(val now : Boolean) : BaseUIState()
    
    object DismissLoading : BaseUIState()
    
    data class ShowToastMessage(val message : String) : BaseUIState()
    
}

