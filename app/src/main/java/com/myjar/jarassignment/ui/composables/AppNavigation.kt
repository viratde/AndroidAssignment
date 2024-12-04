package com.myjar.jarassignment.ui.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myjar.jarassignment.R
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.ui.vm.JarViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: JarViewModel,
) {
    val navController = rememberNavController()
    val navigate = remember { mutableStateOf("") }

    NavHost(
        modifier = modifier
            .background(Color.White),
        navController = navController,
        startDestination = "item_list"
    ) {
        composable("item_list") {
            ItemListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { selectedItem -> navigate.value = selectedItem },
                navigateValue = navigate.value,
                navController = navController
            )
        }
        composable("item_detail/{itemId}") { backStackEntry ->
            BackHandler(
                enabled = true
            ) {
                navigate.value = ""
                navController.navigate("item_list") {
                    popUpTo("item_list") {
                        inclusive = true
                    }
                }
            }
            val itemId = backStackEntry.arguments?.getString("itemId")
            ItemDetailScreen(itemId = itemId, viewModel = viewModel)
        }
    }
}

@Composable
fun ItemListScreen(
    viewModel: JarViewModel,
    onNavigateToDetail: (String) -> Unit,
    navigateValue: String,
    navController: NavHostController
) {
    val items = viewModel.listStringData.collectAsStateWithLifecycle()
    if (navigateValue.isNotBlank()) {
        val currRoute = navController.currentDestination?.route.orEmpty()
        if (!currRoute.contains("item_detail")) {
            navController.navigate("item_detail/$navigateValue")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items.value) { item ->
            ItemCard(
                item = item,
                onClick = { onNavigateToDetail(item.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ItemCard(item: ComputerItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        ItemCardHeaderText(item.name)
        ItemCardDetailsText(item.data?.color, "Color")
        ItemCardDetailsText(item.data?.capacity, "Capacity")
        ItemCardDetailsText(item.data?.price?.let { "$${it}" }, "Price")
        ItemCardDetailsText(item.data?.screenSize?.toString(), "Screen Size")
        ItemCardDetailsText(item.data?.description, "Description")
        ItemCardDetailsText(item.data?.generation, "Generation")
        ItemCardDetailsText(item.data?.strapColour, "Strap Color")
        ItemCardDetailsText(item.data?.caseSize, "Case Size")
        ItemCardDetailsText(item.data?.cpuModel, "Cpu Model")
        ItemCardDetailsText(item.data?.hardDiskSize, "Hard Disk Size")
    }
}



@Composable
fun ItemDetailScreen(
    itemId: String?,
    viewModel: JarViewModel
) {

    var item by remember { mutableStateOf<ComputerItem?>(null) }

    LaunchedEffect(itemId) {
        if (itemId != null) {
            item = viewModel.getItemById(itemId)
        }
    }

    // Fetch the item details based on the itemId
    // Here, you can fetch it from the ViewModel or repository
    if (item != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            ItemCardHeaderText(item!!.name)
            ItemCardDetailsText(item!!.data?.color, "Color")
            ItemCardDetailsText(item!!.data?.capacity, "Capacity")
            ItemCardDetailsText(item!!.data?.price?.let { "$${it}" }, "Price")
            ItemCardDetailsText(item!!.data?.screenSize?.toString(), "Screen Size")
            ItemCardDetailsText(item!!.data?.description, "Description")
            ItemCardDetailsText(item!!.data?.generation, "Generation")
            ItemCardDetailsText(item!!.data?.strapColour, "Strap Color")
            ItemCardDetailsText(item!!.data?.caseSize, "Case Size")
            ItemCardDetailsText(item!!.data?.cpuModel, "Cpu Model")
            ItemCardDetailsText(item!!.data?.hardDiskSize, "Hard Disk Size")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.not_found_item),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }
    }
}
