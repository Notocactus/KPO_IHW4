package se.hse.OrdersApi.stations

import jakarta.persistence.*

@Entity
@Table(name = "stations")
data class Station(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    val station: String
)