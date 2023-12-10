package dev.fmadrid.henrymeds.demo.controller

import dev.fmadrid.henrymeds.demo.controller.koto.CreateAvailabilityRequest
import dev.fmadrid.henrymeds.demo.repository.domain.AvailableSlots
import dev.fmadrid.henrymeds.demo.repository.domain.AvailableTimes
import dev.fmadrid.henrymeds.demo.service.AvailabilityService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/availability")
class AvailabilityController(
        val availabilityService: AvailabilityService
) {
    @PostMapping("/{providerId}/available")
    fun setAvailability(@PathVariable providerId:UUID, @RequestBody request: CreateAvailabilityRequest): AvailableTimes {
        return availabilityService.commitAvailability(providerId, request.startTime, request.endTime)
    }

    @GetMapping("/availableSlots")
    fun getAvailableSlots(@RequestParam providerId: UUID?, @RequestParam requestedDay: String, pageable: Pageable?): Page<AvailableSlots> {
        val requestedTimestamp = Timestamp.from(Instant.parse(requestedDay))
        return availabilityService.getAvailableSlots(providerId, requestedTimestamp, pageable)
    }

}