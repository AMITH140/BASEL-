package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BaselDatabase
import com.example.data.BaselRepository
import com.example.data.DailySummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class BaselViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BaselRepository
    
    private val _todaySummary = MutableStateFlow<DailySummary?>(null)
    val todaySummary: StateFlow<DailySummary?> = _todaySummary.asStateFlow()

    private val _recentSummaries = MutableStateFlow<List<DailySummary>>(emptyList())
    val recentSummaries: StateFlow<List<DailySummary>> = _recentSummaries.asStateFlow()

    init {
        val database = BaselDatabase.getDatabase(application)
        repository = BaselRepository(database.summaryDao())
        
        loadData()
    }
    
    fun loadData() {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            repository.getSummaryForDate(today).collect {
                _todaySummary.value = it
            }
        }
        
        viewModelScope.launch {
            repository.getRecentSummaries().collect {
                _recentSummaries.value = it
            }
        }
    }
}
