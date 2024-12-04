package com.myjar.jarassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import com.myjar.jarassignment.ui.states.ItemsListScreenState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _listStringData = MutableStateFlow(ItemsListScreenState())
    val state: StateFlow<ItemsListScreenState>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch {
            try { // added this because sometimes network is failing
                _listStringData.update {
                    it.copy(
                        computerItems = repository.fetchResults()
                    )
                }
            } catch (err: Exception) {
                if (err is CancellationException) throw err
            }
        }
    }

    fun getItemById(itemId: String): ComputerItem? {
        return _listStringData.value.computerItems.find { it.id == itemId }
    }

    fun updateSelectedItemId(itemId: String?) {
        _listStringData.update {
            it.copy(
                selectedItemId = itemId
            )
        }
    }

    fun updateQuery(query: String) {
        if (query.isNotEmpty()) {
            _listStringData.update {
                it.copy(
                    computerItems = it.computerItems.partition { item ->
                        item.toString().lowercase().contains(query)
                    }.let { it.first + it.second },
                    query = query
                )
            }
        }
    }

}