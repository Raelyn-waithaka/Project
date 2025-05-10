package com.example.giftyhaus.models


data class OrderModel(
    var items: String="",
    var totalAmount: String="",
    var date: String="",
    var status: String="",
    var imageUrl: String="",
    var orderId: String=""
)