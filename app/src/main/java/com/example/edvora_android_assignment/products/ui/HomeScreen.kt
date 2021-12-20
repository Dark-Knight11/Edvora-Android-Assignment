package com.example.edvora_android_assignment.products.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.edvora_android_assignment.common.models.Product
import com.example.edvora_android_assignment.ui.theme.Gray
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun HomeScreen(viewModel: ProductViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val visibility = remember { mutableStateOf(false) }
    val isLoading by viewModel.loading.collectAsState()
    val isError by viewModel.error.collectAsState()
    val states by viewModel.state.collectAsState(listOf())
    val cities by viewModel.city.collectAsState(listOf())
    val shoes by viewModel.shoesData.collectAsState(listOf())
    val apple by viewModel.appleData.collectAsState(listOf())
    val tesla by viewModel.teslaData.collectAsState(listOf())
    val oil by viewModel.oilData.collectAsState(listOf())
    val filteredData by viewModel.filteredData.collectAsState()

    if (isError) Log.i("HomeScreen", "err")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edvora") }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading),
            onRefresh = {
                scope.launch {
                    viewModel.refreshProducts()
                    visibility.value = false
                }
            }
        ) {
            if (!visibility.value)
                LandingContent(
                    states = states,
                    cities = cities,
                    apple = apple,
                    shoes = shoes,
                    tesla = tesla,
                    oil = oil,
                    visibility = visibility,
                    viewModel = viewModel
                )
            else
                FilteredData(
                    products = filteredData,
                    states = states,
                    cities = cities,
                    visibility = visibility,
                    viewModel = viewModel
                )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LandingContent(
    states: List<String>,
    cities: List<String>,
    apple: List<Product>,
    shoes: List<Product>,
    tesla: List<Product>,
    oil: List<Product>,
    visibility: MutableState<Boolean>,
    viewModel: ProductViewModel
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Filters(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                states,
                cities,
                visibility,
                viewModel
            )
        }
        Category("Shoes", shoes)
        Category("Apple", apple)
        Category("Tesla", tesla)
        Category("Oil", oil)
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Category(
    category: String,
    products: List<Product>
) {
    Heading(category)
    Box {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(210.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Black)
        )
        ProductsRow(products = products)
    }
}

@Composable
fun Heading(text: String = "Shoes") {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
    )
    Divider(
        thickness = 1.dp,
        color = Color(0xFFCBCBCB)
    )
}

@Composable
fun ProductsRow(modifier: Modifier = Modifier, products: List<Product>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(35.dp),
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                modifier = modifier
                    .graphicsLayer {
                        shape = RoundedCornerShape(10.dp)
                        clip = true
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilteredData(
    products: List<Product>,
    states: List<String>,
    cities: List<String>,
    visibility: MutableState<Boolean>,
    viewModel: ProductViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Filters(
                modifier = Modifier.padding(horizontal = 20.dp),
                states,
                cities,
                visibility,
                viewModel
            )
            Text(text = "Clear Filter", modifier = Modifier
                .padding(end = 20.dp)
                .clickable { visibility.value = false })
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(20.dp),
        ) {
            items(products) { product ->
                FilterProductCard(
                    product = product,
                    modifier = Modifier.graphicsLayer {
                        shape = RoundedCornerShape(10.dp)
                        clip = true
                    }
                )
            }
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier = Modifier, product: Product) {
    Column(
        modifier = modifier
            .background(Color(0xFF232323))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = rememberImagePainter(product.image),
                    contentDescription = "productImage",
                    modifier = modifier.size(70.dp)
                )
                Spacer(modifier = modifier.height(9.dp))
                product.address?.city?.let { Text(text = it) }
            }
            Spacer(modifier = modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                product.brandName?.let {
                    Text(text = it)
                }
                Text(
                    text = "$ " + product.price.toString(),
                    fontWeight = FontWeight.Bold
                )
                product.date?.let {
                    Text(text = "Date: " + it.slice(0..9))
                }
            }
        }
        product.description?.let { Text(text = it) }
    }
}

@Composable
fun FilterProductCard(modifier: Modifier = Modifier, product: Product) {
    Column(
        modifier = modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(product.image),
            contentDescription = "productImage",
            modifier = modifier.size(90.dp)
        )
        Text(
            text = product.productName,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        product.brandName?.let {
            Text(text = it)
        }
        Text(
            text = "$ " + product.price.toString(),
            fontWeight = FontWeight.Bold
        )
        product.date?.let {
            Text(text = "Date: " + it.slice(0..9))
        }
        product.address?.city?.let { Text(text = it) }
        product.description?.let { Text(text = it) }
    }
}

@ExperimentalMaterialApi
@Composable
fun Filters(
    modifier: Modifier = Modifier,
    states: List<String>,
    cities: List<String>,
    visibility: MutableState<Boolean>,
    viewModel: ProductViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    var expandFilter by remember { mutableStateOf(false) }
    val categories = listOf("Shoes", "Apple", "Tesla", "Oil")
    val filters = listOf("Product", "States", "Cities")
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = modifier
            .background(Color(0xFF232323))
            .wrapContentSize(Alignment.TopStart)
            .graphicsLayer {
                shape = RoundedCornerShape(15.dp)
                clip = true
            }
            .padding(10.dp)
            .clickable { expanded = true }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Filters")
            Spacer(modifier = modifier.width(10.dp))
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "DropDown")
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.background(Color(0xFF131313)),
        offset = DpOffset(20.dp, 10.dp)
    ) {
        filters.forEachIndexed { index, s ->
            DropdownMenuItem(
                onClick = {
                    expanded = false
                }
            ) {
                ListItem(
                    trailing = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown",
                        )
                    },
                    modifier = modifier
                        .width(150.dp)
                        .clickable {
                            expandFilter = true
                            selectedIndex = index
                        },
                    text = {
                        Text(text = s)
                    }
                )
            }
        }
    }
    DropdownMenu(
        expanded = expandFilter,
        onDismissRequest = { expandFilter = false },
        modifier = Modifier.background(Color(0xFF131313)),
        offset = DpOffset(20.dp, 5.dp)
    ) {
        when (selectedIndex) {
            0 -> Options(
                Modifier.clickable { expandFilter = false },
                categories,
                0,
                visibility,
                viewModel
            )
            1 -> Options(
                Modifier.clickable { expandFilter = false },
                states,
                1,
                visibility,
                viewModel
            )
            2 -> Options(
                Modifier.clickable { expandFilter = false },
                cities,
                2,
                visibility,
                viewModel
            )
        }
    }
}

@Composable
fun Options(
    modifier: Modifier,
    list: List<String>,
    index: Int,
    visibility: MutableState<Boolean>,
    viewModel: ProductViewModel
) {
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .height(if (index == 0) 220.dp else 600.dp)
            .width(320.dp)
    ) {
        items(list) { item ->
            Box(modifier = modifier.clickable {
                visibility.value = true
                scope.launch {
                    when (index) {
                        0 -> viewModel.filterByCategory(item)
                        1 -> viewModel.filterByState(item)
                        2 -> viewModel.filterByCity(item)
                    }
                }
            }) {
                Divider(
                    thickness = 1.dp,
                    color = Color(0xFFCBCBCB)
                )
                Text(
                    text = item,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 19.sp
                )
            }
        }
    }
}


