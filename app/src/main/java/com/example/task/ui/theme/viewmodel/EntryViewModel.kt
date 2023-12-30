package com.example.task.ui.theme.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.repository.ApiService
import com.example.task.model.EntryBO
import com.example.task.model.EntryItem
import kotlinx.coroutines.launch

class EntryViewModel : ViewModel() {
    var entryList:List<EntryItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getEntryList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val curList = apiService.getEntry()
                entryList=curList.entries
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}