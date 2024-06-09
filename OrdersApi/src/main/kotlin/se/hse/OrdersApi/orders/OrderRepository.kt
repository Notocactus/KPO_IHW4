package se.hse.OrdersApi.orders

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Int> {
    fun findAllByUserId(userId: Int): MutableList<OrderDto>
}