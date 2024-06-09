package se.hse.OrdersApi.stations

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository: JpaRepository<Station, Int> {
    fun findAllByStation(station: String): MutableList<Station>
    fun findByStation(station: String): Station?
}