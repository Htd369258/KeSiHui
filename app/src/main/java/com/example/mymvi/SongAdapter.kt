package com.example.mymvi

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class SongAdapter :
        BaseQuickAdapter<Song , BaseViewHolder>(R.layout.item_song) {
    
    override fun convert(holder : BaseViewHolder , item : Song) {
    }
}