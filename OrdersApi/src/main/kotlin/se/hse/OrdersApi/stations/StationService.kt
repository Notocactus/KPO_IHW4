package se.hse.OrdersApi.stations

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class StationService (val stationRepository: StationRepository) {

    fun getAllStations() = stationRepository.findAll().map {
            it -> Station(it.id, it.station)
    }

    fun retrieveStationById(id: Int): Optional<StationDto> = stationRepository.findById(id).map {
            it -> StationDto(it.station)
    }

    fun getStationById(id: Int): Station? {
        return stationRepository.findById(id).orElse(null)
    }

    fun retrieveStationByStationName(station: String) = stationRepository.findAllByStation(station)


    @Transactional
    fun tryAddStation(stationDto: StationDto): String {
        val station = stationRepository.findAllByStation(stationDto.station)
        if (station.size == 0) {
            return stationRepository.save(Station(station = stationDto.station)).id.toString()
        }
        return "ERR"
    }
}