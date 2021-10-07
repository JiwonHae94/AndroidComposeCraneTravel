package com.jiwon.practice_crane.home

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.jiwon.practice_crane.base.CraneTabBar
import com.jiwon.practice_crane.base.CraneTabs
import com.jiwon.practice_crane.base.ExploreSection
import com.jiwon.practice_crane.data.ExploreModel
import kotlinx.coroutines.launch

typealias onExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen{
    Fly, Sleep, Eat
}

@Composable
fun CraneHome(
    onExploreItemClicked: onExploreItemClicked,
    onDateSelectedClicked : () -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        drawerContent = {
            CraneDrawer()
        }
    ) {
        val scope = rememberCoroutineScope()
        CraneHomeContent(
            modifier = modifier,
            onExploreItemClicked = onExploreItemClicked,
            onDateSelectionClicked = onDateSelectedClicked,
            openDrawer = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }
}

@Composable
fun CraneDrawer(){

}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CraneHomeContent(
    onExploreItemClicked: onExploreItemClicked,
    onDateSelectionClicked: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()


    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
    var tabSelected by remember { mutableStateOf(CraneScreen.Fly) }

    BackdropScaffold(
        modifier = modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerScrimColor = Color.Unspecified,
        appBar = {
            HomeTabBar(openDrawer, tabSelected, onTabSelected = { tabSelected = it })
        },
        backLayerContent = {
            SearchContent(
                tabSelected,
                viewModel,
                onPeopleChanged,
                onDateSelectionClicked,
                onExploreItemClicked
            )
        },
        frontLayerContent = {
            when (tabSelected) {
                CraneScreen.Fly -> {
                    suggestedDestinations?.let { destinations ->
                        ExploreSection(
                            title = "Explore Flights by Destination",
                            exploreList = destinations,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                }
                CraneScreen.Sleep -> {
                    ExploreSection(
                        title = "Explore Properties by Destination",
                        exploreList = viewModel.hotels,
                        onItemClicked = onExploreItemClicked
                    )
                }
                CraneScreen.Eat -> {
                    ExploreSection(
                        title = "Explore Restaurants by Destination",
                        exploreList = viewModel.restaurants,
                        onItemClicked = onExploreItemClicked
                    )
                }
            }
        }
    )
}

@Composable
private fun HomeTabBar(
    openDrawer : () -> Unit,
    tabSelected : CraneScreen,
    onTabSelected : (CraneScreen) -> Unit,
    modifier : Modifier = Modifier
){
    CraneTabBar(
        modifier = modifier,
        onMenuClicked = openDrawer
    ) { tabBarModifier ->
        CraneTabs(
            modifier = tabBarModifier,
            titles = CraneScreen.values().map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab -> onTabSelected(CraneScreen.values()[newTab.ordinal]) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchContent(
    tabSelected: CraneScreen,
    viewModel: MainViewModel,
    onPeopleChanged: (Int) -> Unit,
    onDateSelectionClicked: () -> Unit,
    onExploreItemClicked: onExploreItemClicked
) {
    // Reading datesSelected State from here instead of passing the String from the ViewModel
    // to cause a recomposition when the dates change.
    val datesSelected = viewModel.datesSelected.toString()

    when (tabSelected) {
        CraneScreen.Fly -> FlySearchContent(
            datesSelected,
            searchUpdates = FlySearchContentUpdates(
                onPeopleChanged = onPeopleChanged,
                onToDestinationChanged = { viewModel.toDestinationChanged(it) },
                onDateSelectionClicked = onDateSelectionClicked,
                onExploreItemClicked = onExploreItemClicked
            )
        )
        CraneScreen.Sleep -> SleepSearchContent(
            datesSelected,
            sleepUpdates = SleepSearchContentUpdates(
                onPeopleChanged = onPeopleChanged,
                onDateSelectionClicked = onDateSelectionClicked,
                onExploreItemClicked = onExploreItemClicked
            )
        )
        CraneScreen.Eat -> EatSearchContent(
            datesSelected,
            eatUpdates = EatSearchContentUpdates(
                onPeopleChanged = onPeopleChanged,
                onDateSelectionClicked = onDateSelectionClicked,
                onExploreItemClicked = onExploreItemClicked
            )
        )
    }
}

data class FlySearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onToDestinationChanged: (String) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: onExploreItemClicked
)

data class SleepSearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: onExploreItemClicked
)

data class EatSearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: onExploreItemClicked
)
