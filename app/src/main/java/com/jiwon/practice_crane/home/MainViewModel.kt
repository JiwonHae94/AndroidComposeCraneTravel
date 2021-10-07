package com.jiwon.practice_crane.home

import androidx.compose.samples.crane.di.DefaultDispatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiwon.practice_crane.calendar.model.DatesSelectedState
import com.jiwon.practice_crane.data.DatesRepository
import com.jiwon.practice_crane.data.DestinationsRepository
import com.jiwon.practice_crane.data.ExploreModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class MainViewModel @Inject constructor(
    private val destinationsRepository: DestinationsRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    datesRepository: DatesRepository
): ViewModel() {
    val hotels: List<ExploreModel> = destinationsRepository.hotels
    val restaurants: List<ExploreModel> = destinationsRepository.restaurants
    val datesSelected: DatesSelectedState = datesRepository.datesSelected

    private val _suggestedDestinations = MutableLiveData<List<ExploreModel>>()
    val suggestedDestinations: LiveData<List<ExploreModel>>
        get() = _suggestedDestinations


    fun updatePeople(people : Int){
        viewModelScope.launch {
            if(people > MAX_PEOPLE){
                _suggestedDestinations.value = emptyList()
            }else{
                val newDestinations = withContext(defaultDispatcher) {
                    destinationsRepository.destinations
                        .shuffled(Random(people * (1..100).shuffled().first()))
                }
                _suggestedDestinations.value = newDestinations
            }
        }
    }


    fun toDestinationChanged(newDestination: String) {
        viewModelScope.launch {
            val newDestinations = withContext(defaultDispatcher) {
                destinationsRepository.destinations
                    .filter { it.city.nameToDisplay.contains(newDestination) }
            }
            _suggestedDestinations.value = newDestinations
        }
    }

    companion object{
        const val MAX_PEOPLE = 4

    }
}