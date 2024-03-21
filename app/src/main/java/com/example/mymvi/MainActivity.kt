package com.example.mymvi

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mymvi.databinding.MainActivityBinding

class MainActivity : BaseActivity<MainActivityBinding , MainViewModel>() {

    override fun onBindViewModel() : Class<MainViewModel> = MainViewModel::class.java
    
    override fun getLayoutResID() : Int = R.layout.main_activity
    
    override fun initView(savedInstanceState : Bundle?) {
    }
    
    override fun onClickListener() {
        val  meettiings1= mutableListOf<List<Int>>(
                listOf(16,20),
                listOf(5,10),
                listOf(11,12),
                listOf(8,15))
        val  meettiings2= mutableListOf<List<Int>>(
                listOf(1,3),
                listOf(6,10),
                listOf(4,5),
                listOf(11,15))
        
        viewDataBinding.calculate1.setOnClickListener {
            viewModel.emitIsAttendAllMeetingsIntent(meettiings1)
        }
        
        viewDataBinding.calculate2.setOnClickListener {
            viewModel.emitIsAttendAllMeetingsIntent(meettiings2)
        }
        viewDataBinding.click.setOnClickListener {
            SearchSongActivity.launch(this)
        }
  
    }
    override fun onUIStateListener(baseUIState : BaseUIState) {
        when(baseUIState){
            is MainUIState.AttendAllMeetingsState->{
                viewDataBinding.result1.text=  "会议数据：${baseUIState.meetings.toMutableList()} ，一个人是否可以参加完所有会议结果为：${baseUIState.isAttendAllMeetings}  "
            }
    
            else                             -> {
            
            }
        }
    }
}
