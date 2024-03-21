package com.example.mymvi

import android.app.Application
import android.util.Log

data class MainData(val id : Int)

sealed class MainUIState : BaseUIState() {
    data class AttendAllMeetingsState(
            val isAttendAllMeetings : Boolean ,
            val meetings : MutableList<List<Int>>
                                     ) : MainUIState()
}

sealed class MainIntent : BaseIntent() {
    data class IsAttendAllMeetings(val meetings : MutableList<List<Int>>) : MainIntent()
}

class MainViewModel(application : Application) : BaseViewModel(application) {
    
    override suspend fun onIntentListener(intent : BaseIntent) {
        when (intent) {
            is MainIntent.IsAttendAllMeetings -> {
                emitIsAttendAllMeetings(intent.meetings)
            }
            
            else                              -> {}
        }
    }
    
    private fun emitIsAttendAllMeetings(meetings : MutableList<List<Int>>) {
        emitUIState(MainUIState.AttendAllMeetingsState(isAttendAllMeetings(meetings) , meetings))
    }
    
   
    private fun isAttendAllMeetings(meetings : MutableList<List<Int>>) : Boolean {
        //1，先排好序
        val length = meetings.size
        for (i in 0 until length) {
            for (j in 0 until length - i - 1) {
                if (meetings[j][0] > meetings[j + 1][0]) {
                    val temp = meetings[j]
                    meetings[j] = meetings[j + 1]
                    meetings[j + 1] = temp
                }
            }
        }
        //2.进行比较
        val l = length - 1
        meetings.forEachIndexed { index , metting ->
            if (index < l && meetings[index + 1][0] <= metting[1])
                return false
        }
        return true
    }
    
    fun emitIsAttendAllMeetingsIntent(meetings : MutableList<List<Int>>) {
        sendIntent(MainIntent.IsAttendAllMeetings(meetings))
    }
}