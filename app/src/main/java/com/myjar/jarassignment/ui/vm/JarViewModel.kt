package com.myjar.jarassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())

    fun fetchData() {
        viewModelScope.launch {
            try { // added this because sometimes network is failing
                _listStringData.update {
                    repository.fetchResults()
                }
            } catch (err: Exception) {
                if (err is CancellationException) throw err
            }
        }
    }

    fun getItemById(itemId: String): ComputerItem? {
        return _listStringData.value.find { it.id == itemId }
    }

    fun sortComputerItemsByQuery(query: String) {
        _listStringData.update {
            val result = it.partition { item ->
                item.toString().lowercase().contains(query)
            }
            result.first + result.second
        }
    }

}