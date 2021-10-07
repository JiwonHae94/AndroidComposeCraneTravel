package com.jiwon.practice_crane.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jiwon.practice_crane.R
import com.jiwon.practice_crane.base.SimpleUserInput


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlySearchContent(datesSelected: String, searchUpdates: FlySearchContentUpdates) {
    CraneSearch {
        PeopleUserInput(
            titleSuffix = ", Economy",
            onPeopleChanged = searchUpdates.onPeopleChanged
        )
        Spacer(Modifier.height(8.dp))
        FromDestination()
        Spacer(Modifier.height(8.dp))
        ToDestinationUserInput(onToDestinationChanged = searchUpdates.onToDestinationChanged)
        Spacer(Modifier.height(8.dp))
        DatesUserInput(datesSelected, onDateSelectionClicked = searchUpdates.onDateSelectionClicked)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SleepSearchContent(datesSelected: String, sleepUpdates: SleepSearchContentUpdates) {
    CraneSearch {
        PeopleUserInput(onPeopleChanged = { sleepUpdates.onPeopleChanged })
        Spacer(Modifier.height(8.dp))
        DatesUserInput(datesSelected, onDateSelectionClicked = sleepUpdates.onDateSelectionClicked)
        Spacer(Modifier.height(8.dp))
        SimpleUserInput(caption = "Select Location", vectorImageId = R.drawable.ic_hotel)
    }
}

@ExperimentalMaterialApi
@Composable
fun EatSearchContent(datesSelected: String, eatUpdates: EatSearchContentUpdates) {
    CraneSearch {
        PeopleUserInput(onPeopleChanged = { eatUpdates.onPeopleChanged })
        Spacer(Modifier.height(8.dp))
        DatesUserInput(datesSelected, onDateSelectionClicked = eatUpdates.onDateSelectionClicked)
        Spacer(Modifier.height(8.dp))
        SimpleUserInput(caption = "Select Time", vectorImageId = R.drawable.ic_time)
        Spacer(Modifier.height(8.dp))
        SimpleUserInput(caption = "Select Location", vectorImageId = R.drawable.ic_restaurant)
    }
}

@Composable
private fun CraneSearch(content: @Composable () -> Unit) {
    Column(Modifier.padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 12.dp)) {
        content()
    }
}

