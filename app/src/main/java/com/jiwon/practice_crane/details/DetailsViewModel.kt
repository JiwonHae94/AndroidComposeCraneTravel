package com.jiwon.practice_crane.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jiwon.practice_crane.base.Result
import com.jiwon.practice_crane.data.DestinationsRepository
import com.jiwon.practice_crane.data.ExploreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val destinationsRepository : DestinationsRepository,
    savedStateHandle:SavedStateHandle
): ViewModel() {
    private val cityName = savedStateHandle.get<String>(KEY_ARG_DETAILS_CITY_NAME)!!

    val cityDetails: Result<ExploreModel>
        get() {
            val destination = destinationsRepository.getDestination(cityName)
            return if (destination != null) {
                Result.Success(destination)
            } else {
                Result.Error(IllegalArgumentException("City doesn't exist"))
            }
        }
}