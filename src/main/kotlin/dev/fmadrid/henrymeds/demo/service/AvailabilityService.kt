package dev.fmadrid.henrymeds.demo.service

import dev.fmadrid.henrymeds.demo.repository.AvailableTimesRepository
import dev.fmadrid.henrymeds.demo.repository.ProviderRepository
import dev.fmadrid.henrymeds.demo.repository.domain.AvailableSlots
import dev.fmadrid.henrymeds.demo.repository.domain.AvailableTimes
import dev.fmadrid.henrymeds.demo.repository.domain.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class AvailabilityService(val availableTimesRepository: AvailableTimesRepository, val providerRepository: ProviderRepository) {
    fun getAvailableSlots(providerId: UUID?, requestedStart: Timestamp, pageable: Pageable? = Pageable.ofSize(10)): Page<AvailableSlots> {
        val plusOneDay = Timestamp.from(requestedStart.toInstant().plus(1L, ChronoUnit.DAYS))
        // assuming providers schedule day by day... pretty big one
        // get all available providers available today
        var availableProvidersTimes = availableTimesRepository.findAllByStartDateAfterAndEndDateBeforeAndStatusEquals(requestedStart, plusOneDay, Status.OPEN)
        // if they requested a specific provider, filter others out. If it returns empty list, shortcut...
        if (providerId != null) {
            availableProvidersTimes = availableProvidersTimes.filter { it.provider.id == providerId }
        }
        if (availableProvidersTimes.isEmpty()) return pageable?.let { Page.empty(it) } ?: Page.empty()


        // Go through all available slots and figure out how many slots are available
        val slots = availableProvidersTimes.flatMap {
            var duration = Duration.between(it.startDate.toInstant(), it.endDate.toInstant())
            val numSlots = duration.dividedBy(FIFTEEN_MIN)
            var failLock = 0L
            val currentDuration = it.startDate.toInstant()
            val availableSlots = mutableListOf<AvailableSlots>()
            // TODO figure out why this only starts from the "end Date" and repeats
            while (!duration.isZero && failLock != numSlots) {
                availableSlots.add(AvailableSlots(Timestamp.from(currentDuration), Timestamp.from(currentDuration.plus(15L, ChronoUnit.MINUTES)), it.provider.id.toString()))
                currentDuration.plus(15L, ChronoUnit.MINUTES)
                failLock += 1
                duration = duration.minus(FIFTEEN_MIN)
            }
            availableSlots
        }
        return PageImpl(slots)
    }

    fun commitAvailability(providerId: UUID, startTime: Timestamp, endTime: Timestamp): AvailableTimes {
        val provider = providerRepository.findById(providerId).getOrNull()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find provider")
        return availableTimesRepository.save(AvailableTimes(
                id = UUID.randomUUID(), startDate = startTime,
                endDate = endTime, provider = provider, status = Status.OPEN
        ))
    }

    companion object {
        val FIFTEEN_MIN: Duration = Duration.ofMinutes(15)
    }
}