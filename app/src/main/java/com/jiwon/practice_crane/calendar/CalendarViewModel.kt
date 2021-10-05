package com.jiwon.practice_crane.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiwon.practice_crane.calendar.model.DaySelected
import com.jiwon.practice_crane.data.DatesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val datesRepository : DatesRepository
) : ViewModel(){
    val datesSelected = datesRepository.datesSelected
    val calendarYear = datesRepository.calendarYear

    fun onDaySelected(daySelected: DaySelected){
        viewModelScope.launch {
            datesRepository.onDaySelected(daySelected)
        }
    }

}
