package com.example.mymvi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymvi.databinding.SearchSongActivityBinding

class SearchSongActivity : BaseActivity<SearchSongActivityBinding , SearchSongViewModel>() {
    
    override fun onBindViewModel() : Class<SearchSongViewModel> = SearchSongViewModel::class.java
    
    override fun getLayoutResID() : Int = R.layout.search_song_activity
    
    override fun initView(savedInstanceState : Bundle?) {
        viewDataBinding.rv.adapter = viewModel.adapter
        viewDataBinding.rv.layoutManager = LinearLayoutManager(this.application)
        viewModel.adapter.setList(
                listOf(
                        Song("小明" , "小小鸟" , "￥80" , "高质量" , "12232423432") ,
                        Song("小明" , "小小鸟" , "￥80" , "高质量" , "12232423432") ,
                        Song("小明" , "小小鸟" , "￥80" , "高质量" , "12232423432") ,
                        Song("小明" , "小小鸟" , "￥80" , "高质量" , "12232423432") ,
                        Song("小明" , "小小鸟" , "￥80" , "高质量" , "12232423432")
                      )
                                 )
    }
    
    override fun onClickListener() {
        viewDataBinding.search.setOnEditorActionListener { p0 , p1 , p2 ->
            
            true
        }
    }
    
    override fun onUIStateListener(baseUIState : BaseUIState) {
    
    }
    
    companion object {
        
        fun launch(context : Context) {
            context.startActivity(Intent(context , SearchSongActivity::class.java))
        }
    }
}
