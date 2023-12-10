package dev.fmadrid.henrymeds.demo.repository.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.sql.Timestamp
import java.util.*

@Entity
data class Appointment(
        @Id val id: UUID = UUID.randomUUID(),
        val startTime: Timestamp,
        val endTime: Timestamp,
        @ManyToOne
        val scheduledBy: Client,
        @ManyToOne
        val requestedProvider: Provider,
        val status: Status = Status.OPEN
)