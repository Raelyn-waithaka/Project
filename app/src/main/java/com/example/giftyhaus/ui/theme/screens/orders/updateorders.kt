import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.giftyhaus.R

@Composable
fun UpdateOrderScreen(
    productId: String
) {
    var orderDetails by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(painter = painterResource(id =R.drawable.logo ),
            contentDescription = "GiftyHaus",
            modifier = Modifier.size(500.dp)
        )
        Text(text = "Update Order", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = orderDetails,
            onValueChange = { orderDetails = it },
            label = { Text("Order Details") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Order Status") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {(orderDetails) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update Order")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun UpdateOrderScreenPreview() {
    UpdateOrderScreen(rememberNavController().toString())
}
