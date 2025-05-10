package com.example.giftyhaus.ui.theme.screens.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.giftyhaus.R

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class CartItem(val name: String, val quantity: Int, val price: Double)

@Composable
fun PlaceOrderScreen(navController: NavController) {
    val cartItems = remember {
        listOf(
            CartItem("T-shirt", 2, 20.0),
            CartItem("Sneakers", 1, 50.0)
        )
    }

    val address = "Gifty Lane, Jetpack City, Nairobi"
    val context = LocalContext.current

    val totalPrice = cartItems.sumOf { it.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Shipping Address", fontSize = 18.sp)
        Text(text = address, fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))

        Text(text = "Order Summary", fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                Text(
                    text = "${item.quantity} x ${item.name} @ \$${item.price}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Text(text = "Total: \$${"%.2f".format(totalPrice)}", fontSize = 20.sp, modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Place Order")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaceOrderScreenPreview(){
    PlaceOrderScreen(rememberNavController())
}