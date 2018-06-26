package com.voltek.newsfeed.base.presentation.adapter

import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate

abstract class BaseDelegate<I : AdapterItem, VH : BaseViewHolder>
    : AbsListItemAdapterDelegate<I, AdapterItem, VH>()
