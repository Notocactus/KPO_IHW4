package se.hse.OrdersApi.orders

import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
@NoArgsConstructor
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name="UserId")
    val userId: Int,

    @Column(name="FromStationId")
    val fromStationId: Int,

    @Column(name="ToStationId")
    val toStationId: Int,

    var status: Int,

    var created: Timestamp
    )