package com.jiwon.practice_crane.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.jiwon.practice_crane.calendar.model.CalendarDay
import com.jiwon.practice_crane.calendar.model.CalendarMonth
import com.jiwon.practice_crane.data.CalendarYear
import com.jiwon.practice_crane.ui.theme.Practice_craneTheme
import com.jiwon.practice_crane.R
import com.jiwon.practice_crane.calendar.model.DaySelected


@ExperimentalMaterialApi
fun lanchCalendarActivity(context: Context){
    val intent = Intent(context, CalendarActivity::class.java)
    context.startActivity(intent)
}

@ExperimentalMaterialApi
class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                Practice_craneTheme {
                    CalendarScreen(onBackPressed = { finish() })
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ContentView(){
    ProvideWindowInsets {
        Practice_craneTheme {
            CalendarScreen(onBackPressed = {  })
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun CalendarScreen(
    onBackPressed : () -> Unit,
    calendarViewModel: CalendarViewModel = viewModel()
){
    val calendarYear = calendarViewModel.calendarYear

    CalendarContent(
        selectedDates = calendarViewModel.datesSelected.toString(),
        calendarYear = calendarYear,
        onDayClicked = { calendarDay, calendarMonth ->
            calendarViewModel.onDaySelected(
                DaySelected(calendarDay.value.toInt(), calendarMonth, calendarYear)
            )
        },
        onBackPressed = onBackPressed
    )
}

@ExperimentalMaterialApi
@Composable
fun CalendarContent(
    selectedDates : String,
    calendarYear : CalendarYear,
    onDayClicked : (CalendarDay, CalendarMonth) -> Unit,
    onBackPressed: () -> Unit
){
    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        topBar = {
            CalendarTopAppBar(selectedDates, onBackPressed)
        }
    ) {
        Calendar(calendarYear, onDayClicked)
    }
}

@Composable
private fun CalendarTopAppBar(selectedDates : String, onBackPressed: () -> Unit){
    Column{
        Spacer(
            modifier = Modifier
                .statusBarsHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
        )

        TopAppBar(
            title = {
                Text(
                    text = if(selectedDates.isEmpty()) "Select Dates" else selectedDates
                )
            },
            navigationIcon= {
                IconButton(onClick = { onBackPressed() }){
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.cd_back)
                    )
                }
            },
            backgroundColor =  MaterialTheme.colors.primaryVariant,
            elevation = 0.dp
        )
    }
}