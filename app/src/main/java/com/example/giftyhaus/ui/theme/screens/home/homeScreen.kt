package com.example.giftyhaus.ui.theme.screens.home


import com.example.giftyhaus.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.giftyhaus.models.ProductModel
import com.example.giftyhaus.navigation.ROUTE_ADD_PRODUCT
import com.example.giftyhaus.navigation.ROUTE_VIEW_ORDERS
import com.example.giftyhaus.navigation.navigateToCategory

data class Category(val name: String, val imageRes: Int)

@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf(
        Category("Shirts", R.drawable.tshirt),
        Category("Sneakers", R.drawable.sneakers),
        Category("Bags", R.drawable.backpack)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text("Welcome to GiftyHaus Shop!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(categories.size) { index ->
                    val category = categories[index]
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(navigateToCategory(category.name))
                            }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = category.imageRes),
                                contentDescription = category.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = category.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(ROUTE_ADD_PRODUCT) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Product",
                tint = Color.White
            )
        }
//        Button(
//            onClick = { navController.navigate(ROUTE_VIEW_ORDERS) },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = "View Orders")
//        }


    }



}



@Composable
fun ProductCard(product: ProductModel, onClick: (ProductModel) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product)}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = product.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text(text = "Ksh ${product.price}", color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen (rememberNavController())
}

