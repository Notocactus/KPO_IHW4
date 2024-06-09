package se.hse.OrdersApi.stations

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stations")
class StationController(val stationService: StationService) {

    @GetMapping
    fun getAllStations() : ResponseEntity<List<Station>> {
        return ResponseEntity.ok(stationService.getAllStations())
    }

    @GetMapping("/get/{id}")
    fun getStationWithId(@PathVariable("id") id: Int) : ResponseEntity<String> {
        val station = stationService.getStationById(id) ?: return ResponseEntity<String>("No such station", HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(station.station)
    }

//    @PostMapping("/get")
//    fun getStationByTitle(@RequestBody station: String) : ResponseEntity<String> {
//        val stationResult = stationService.retrieveStationByStationName(station)
//        if (stationResult.size != 0){
//            val id = stationResult[0].id
//            return ResponseEntity.ok("{\"id\": $id}")
//        }
//        return ResponseEntity<String>("No such station", HttpStatus.NOT_FOUND)
//    }

    @PostMapping("/add")
    fun postAStation(@RequestBody station: StationDto) : ResponseEntity<String> {
        return try {
            val resp = stationService.tryAddStation(station)
            if (resp == "ERR") {
                ResponseEntity<String>("Station already exists", HttpStatus.BAD_REQUEST)
            }
            ResponseEntity.ok(resp)
        } catch (e: Exception) {
            ResponseEntity<String>("Station already exists", HttpStatus.BAD_REQUEST)
        }
    }
}