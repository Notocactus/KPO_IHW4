package se.hse.OrdersApi.orders

class OrderDto (
    val userId: Int,
    val fromStationId: Int,
    val toStationId: Int,
    val status: Int,
)
