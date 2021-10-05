package com.jiwon.practice_crane.data

import androidx.compose.samples.crane.di.DefaultDispatcher
import com.jiwon.practice_crane.calendar.model.DatesSelectedState
import com.jiwon.practice_crane.calendar.model.DaySelected
import com.jiwon.practice_crane.calendar.model.DaySelectedStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatesRepository @Inject constructor(
    datesLocalDataSource : DatesLocalDataSource,
    @DefaultDispatcher private val defaultDispatcher : CoroutineDispatcher
){
    val calendarYear = datesLocalDataSource.year2020
    val datesSelected = DatesSelectedState(datesLocalDataSource.year2020)

    suspend fun onDaySelected(daySelected : DaySelected) = withContext(defaultDispatcher){
        datesSelected.daySelected(daySelected)
    }
}