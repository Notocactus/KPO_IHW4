package se.hse.OrdersApi.orders

class AddOrderDto (
    val fromStationId: Int,
    val toStationId: Int,
    val token: String
)