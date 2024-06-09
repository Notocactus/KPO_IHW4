package se.hse.OrdersApi.orders

import khttp.responses.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lombok.Value
import org.json.JSONObject
import org.springframework.boot.json.GsonJsonParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/orders")
class OrderController (val orderService: OrderService) {

    @GetMapping
    fun getAllOrders() : ResponseEntity<List<OrderDto>> {
        return ResponseEntity.ok(orderService.getAllOrders())
    }

    @GetMapping("/get/{id}")
    fun getOrderWithId(@PathVariable("id") id: Int) : ResponseEntity<Order> {
        val order = orderService.getOrderById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(order)
    }

    @GetMapping("/get")
    fun getOrderByUserId(@RequestParam userId: Int) : ResponseEntity<OrderDto> {
        val order = orderService.retrieveOrderByUserId(userId).first()
        return ResponseEntity.ok(order)
    }

    suspend fun validate(id: Int) {
        val rnd = (1000L..3000L).random()
        delay(rnd)
        val rnds = (2..3).random()
        orderService.changeOrderStatus(id, rnds)
    }

    @PostMapping("/add")
    fun postAnOrder(@RequestBody addOrderDto: AddOrderDto) : ResponseEntity<String> {
        return try {
            if (addOrderDto.fromStationId == addOrderDto.toStationId) {
                ResponseEntity<String>("Stations should be different", HttpStatus.BAD_REQUEST)
            }

            val response : Response = khttp.post(
//                url = "http://localhost:8080/user/userinfo",
                url = "http://auth-service:8080/user/userinfo",
                json = mapOf("token" to addOrderDto.token))
            if (response.statusCode != 200) {
                ResponseEntity<String>("Something is wrong with the token", HttpStatus.BAD_REQUEST)
            }
            val obj = JSONObject(response.text)
//            val obj : JSONObject = response.jsonObject
            var order = OrderDto(obj["id"].toString().toInt(), addOrderDto.fromStationId, addOrderDto.toStationId, 1)
//            val obj : String = response.text
//            var order = OrderDto(obj.toInt(), addOrderDto.fromStationId, addOrderDto.toStationId, 1)
            val resp = orderService.tryAddOrder(order)
            if (resp == "ERR") {
                ResponseEntity<String>("Stations don't exist", HttpStatus.BAD_REQUEST)
            }
            runBlocking{
                launch(Dispatchers.IO) {
                    validate(resp.toInt())
                }
            }
            ResponseEntity.ok("Order created successfully")
        } catch (e: Exception) {
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}