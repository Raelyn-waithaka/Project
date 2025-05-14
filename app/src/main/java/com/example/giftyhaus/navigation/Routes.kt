package com.example.giftyhaus.navigation


const val ROUTE_REGISTER ="register"
const val ROUTE_LOGIN = "login"
const val ROUTE_TOP_BAR ="topbar"
const val ROUTE_HOME = "home"
const val ROUTE_SPLASH = "splashscreen"
const val ROUTE_VIEW_ORDERS = "vieworders"
const val ROUTE_UPDATE_PRODUCTS = "updateorder"
const val ROUTE_ADD_PRODUCT ="addproduct"

const val ROUTE_CATEGORY_PRODUCTS = "category_products/{category}"

fun navigateToCategory(category: String) = "category_products/$category"

