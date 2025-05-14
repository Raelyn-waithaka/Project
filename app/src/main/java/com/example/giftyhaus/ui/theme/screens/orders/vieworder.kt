package com.example.giftyhaus.ui.theme.screens.products


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.giftyhaus.data.ProductViewModel
import com.example.giftyhaus.models.ProductModel


@Composable
fun ViewProductScreen(navController: NavHostController) {
    val context = LocalContext.current
    val productRepository = ProductViewModel()
    val products = remember { mutableStateListOf<ProductModel>() }

    // Fetch all products
    productRepository.viewProducts(products)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Products",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(products) { product ->
                ProductItem(
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    category = product.category,
                    imageUrl = product.imageUrl,
                    productId = product.productId,
                    navController = navController,
                    productRepository = productRepository
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    name: String,
    price: String,
    description: String,
    category: String,
    imageUrl: String,
    productId: String,
    navController: NavHostController,
    productRepository: ProductViewModel
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(250.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Row {
            Column {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp)
                        .padding(10.dp)
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = {
//                            productRepository.deleteProduct(context, productId, navController)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(text = "REMOVE", color = Color.Black)
                    }
                    Button(
                        onClick = {
                            val ROUTE_UPDATE_PRODUCT = ""
                            navController.navigate("$ROUTE_UPDATE_PRODUCT/$productId")
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(Color.Green)
                    ) {
                        Text(text = "UPDATE", color = Color.Black)
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Text(text = "PRODUCT NAME", color = Color.Black)
                Text(text = name, color = Color.White)
                Text(text = "PRICE", color = Color.Black)
                Text(text = price, color = Color.White)
                Text(text = "DESCRIPTION", color = Color.Black)
                Text(text = description, color = Color.White)
                Text(text = "CATEGORY", color = Color.Black)
                Text(text = category, color = Color.White)
            }
        }
    }
}
