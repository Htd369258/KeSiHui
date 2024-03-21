package com.example.mymvi

import android.app.Application
import android.util.Log



sealed class SearchSongUIState : BaseUIState() {
    data class AttendAllMeetingsState(
            val isAttendAllMeetings : Boolean ,
            val meetings : MutableList<List<Int>>
                                     ) : MainUIState()
}

sealed class SearchSongIntent : BaseIntent() {
    data class IsAttendAllMeetings(val meetings : MutableList<List<Int>>) : MainIntent()
}

class SearchSongViewModel(application : Application) : BaseViewModel(application) {
    
    override suspend fun onIntentListener(intent : BaseIntent) {
        when (intent) {
            
            
            else                              -> {}
        }
    }
    val adapter : SongAdapter  by lazy {
        SongAdapter()
    }

}