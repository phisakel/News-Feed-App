package com.voltek.newsfeed.base.presentation.adapter

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

typealias Items = List<AdapterItem>

class BaseRecyclerAdapter(
        vararg delegates: AdapterDelegate<Items>,
        items: Items = emptyList()
) : ListDelegationAdapter<Items>() {

    init {
        this.items = items
        for (delegate in delegates) {
            delegatesManager.addDelegate(delegate)
        }
    }

    fun getItem(index: Int) = items.getOrNull(index)

    override fun getItemId(position: Int): Long {
        return getItem(position)?.getUniqueId() ?: 0L
    }

    override fun setItems(items: Items) {
        super.setItems(items)
        notifyDataSetChanged()
    }

    fun clear() {
        setItems(emptyList())
    }
}
