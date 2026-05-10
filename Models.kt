package com.gcollector.models

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = ""
)

data class ScrapItem(
    val name: String,
    val pricePerKg: Double,
    var weight: Double = 0.0
) {
    val amount: Double get() = weight * pricePerKg
}

data class CartEntry(
    val name: String,
    val amount: Double
)

data class HistoryItem(
    val date: String,
    val items: List<CartEntry>,
    val amt: Double,
    val eco: Int
)

data class Post(
    val text: String,
    var likes: Int = 0
)
