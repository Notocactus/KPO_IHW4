package se.hse.OrdersApi.orders

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import se.hse.OrdersApi.stations.StationService
import java.sql.Timestamp
import java.time.LocalDateTime

import java.util.*

@Service
class OrderService (val orderRepository: OrderRepository, val stationService: StationService) {

    fun getAllOrders() = orderRepository.findAll().map {
            it -> OrderDto(it.userId, it.fromStationId, it.toStationId, it.status)
    }

    fun retrieveOrderById(id: Int): Optional<OrderDto> = orderRepository.findById(id).map {
            it -> OrderDto(it.userId, it.fromStationId, it.toStationId, it.status)
    }

    fun getOrderById(id: Int): Order? {
        return orderRepository.findById(id).orElse(null)
    }

    fun retrieveOrderByUserId(userId: Int) = orderRepository.findAllByUserId(userId)

    @Transactional
    fun tryAddOrder(orderDto: OrderDto): String {
        val fromStation = stationService.retrieveStationById(orderDto.fromStationId)
        val toStation = stationService.retrieveStationById(orderDto.toStationId)
        if (!fromStation.isEmpty && !toStation.isEmpty) {
            return orderRepository.save(Order(userId = orderDto.userId,
                fromStationId = orderDto.fromStationId,
                toStationId = orderDto.toStationId,
                status = orderDto.status, created = Timestamp.valueOf(LocalDateTime.now()))).id.toString()
        }
        return "ERR"
    }

    fun changeOrderStatus(id: Int, new_status: Int) {
        var order = getOrderById(id)
        order?.apply { status = new_status }
        orderRepository.save(order!!)
    }
}