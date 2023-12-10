package dev.fmadrid.henrymeds.demo.repository

import dev.fmadrid.henrymeds.demo.repository.domain.Appointment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AppointmentRepository : JpaRepository<Appointment, UUID> {
    fun getAllByScheduledById(id: UUID, pageable: Pageable): Page<Appointment>

    fun getAllByRequestedProviderId(id: UUID, pageable: Pageable): Page<Appointment>
}