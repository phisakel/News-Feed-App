package com.voltek.newsfeed.base.presentation.adapter

interface AdapterItem {

    /**
     * Used to calculate diff between old and news lists,
     * when calling [android.support.v7.widget.RecyclerView.Adapter.notifyDataSetChanged]
     */
    fun getUniqueId(): Long
}
