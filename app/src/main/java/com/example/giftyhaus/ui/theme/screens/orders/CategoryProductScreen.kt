package com.example.giftyhaus.ui.theme.screens.orders

import com.example.giftyhaus.data.ProductViewModel



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.giftyhaus.models.ProductModel
import com.example.giftyhaus.ui.theme.screens.home.ProductCard

@Composable
fun CategoryProductsScreen(category: String, navController: NavController) {
    val viewModel: ProductViewModel = viewModel()
    val products = remember { mutableStateListOf<ProductModel>() }

    LaunchedEffect(category) {
        viewModel.fetchProductsByCategory(category, products)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Category: $category", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        if (products.isEmpty()) {
            Text("No products in this category.", modifier = Modifier.padding(top = 16.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(products.size) { index ->
                    ProductCard(product = products[index], onClick = { /* handle click */ })
                }
            }
        }
    }
}