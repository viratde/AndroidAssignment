package com.myjar.jarassignment.ui.states

import com.myjar.jarassignment.data.model.ComputerItem

data class ItemsListScreenState(
    val computerItems: List<ComputerItem> = emptyList(),
    val query: String = "",
    val selectedItemId: String? = null
)
