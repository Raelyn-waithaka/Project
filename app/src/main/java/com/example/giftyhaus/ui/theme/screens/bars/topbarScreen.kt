package com.example.giftyhaus.ui.theme.screens.bars



import com.example.giftyhaus.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun TopBarUI() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFF1A1A1A)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Cart Icon",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "GiftyHaus",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search Icon",
            tint = Color(0xFFFFD700),
            modifier = Modifier
                .padding(end = 16.dp)
                .size(24.dp)
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2B2B2B))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TabItem("Shopping Page")
        TabItem("Online Shop")
    }
}

@Composable
fun TabItem(text: String) {
    Text(
        text = text,
        color = Color(0xFFFFD700),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF404040))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTopBarUI() {
    TopBarUI()
}

