/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiwon.practice_crane.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jiwon.practice_crane.R
import com.jiwon.practice_crane.base.CraneEditableUserInput
import com.jiwon.practice_crane.base.CraneUserInput
import com.jiwon.practice_crane.home.MainViewModel.Companion.MAX_PEOPLE
import com.jiwon.practice_crane.ui.theme.Practice_craneTheme

enum class PeopleUserInputAnimationState { Valid, Invalid }

class PeopleUserInputState {
    var people by mutableStateOf(1)
        private set

    val animationState: MutableTransitionState<PeopleUserInputAnimationState> =
        MutableTransitionState(PeopleUserInputAnimationState.Valid)

    fun addPerson() {
        people = (people % (MAX_PEOPLE + 1)) + 1
        updateAnimationState()
    }

    private fun updateAnimationState() {
        val newState =
            if (people > MAX_PEOPLE) PeopleUserInputAnimationState.Invalid
            else PeopleUserInputAnimationState.Valid

        if (animationState.currentState != newState) animationState.targetState = newState
    }
}

@ExperimentalMaterialApi
@Composable
fun PeopleUserInput(
    titleSuffix: String? = "",
    onPeopleChanged: (Int) -> Unit,
    peopleState: PeopleUserInputState = remember { PeopleUserInputState() }
) {
    Column {
        val transitionState = remember { peopleState.animationState }
        val tint = tintPeopleUserInput(transitionState)

        val people = peopleState.people
        CraneUserInput(
            text = if (people == 1) "$people Adult$titleSuffix" else "$people Adults$titleSuffix",
            vectorImageId = R.drawable.ic_person,
            tint = tint.value,
            onClick = {
                peopleState.addPerson()
                onPeopleChanged(peopleState.people)
            }
        )
        if (transitionState.targetState == PeopleUserInputAnimationState.Invalid) {
            Text(
                text = "Error: We don't support more than $MAX_PEOPLE people",
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.secondary)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FromDestination() {
    CraneUserInput(text = "Seoul, South Korea", vectorImageId = R.drawable.ic_location)
}

@ExperimentalMaterialApi
@Composable
fun ToDestinationUserInput(onToDestinationChanged: (String) -> Unit) {
    CraneEditableUserInput(
        hint = "Choose Destination",
        caption = "To",
        vectorImageId = R.drawable.ic_plane,
        onInputChanged = onToDestinationChanged
    )
}

@ExperimentalMaterialApi
@Composable
fun DatesUserInput(datesSelected: String, onDateSelectionClicked: () -> Unit) {
    CraneUserInput(
        onClick = onDateSelectionClicked,
        caption = if (datesSelected.isEmpty()) "Select Dates" else null,
        text = datesSelected,
        vectorImageId = R.drawable.ic_calendar
    )
}

@Composable
private fun tintPeopleUserInput(
    transitionState: MutableTransitionState<PeopleUserInputAnimationState>
): State<Color> {
    val validColor = MaterialTheme.colors.onSurface
    val invalidColor = MaterialTheme.colors.secondary

    val transition = updateTransition(transitionState)
    return transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }
    ) {
        if (it == PeopleUserInputAnimationState.Valid) validColor else invalidColor
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PeopleUserInputPreview() {
    Practice_craneTheme() {
        PeopleUserInput(onPeopleChanged = {})
    }
}
