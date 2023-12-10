package dev.fmadrid.henrymeds.demo.repository

import dev.fmadrid.henrymeds.demo.repository.domain.AvailableTimes
import dev.fmadrid.henrymeds.demo.repository.domain.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp
import java.util.UUID

interface AvailableTimesRepository : JpaRepository<AvailableTimes, UUID> {
    fun findAllByStartDateAfterAndEndDateBeforeAndStatusEquals(startDate: Timestamp, endDate: Timestamp, status: Status): List<AvailableTimes>
}
