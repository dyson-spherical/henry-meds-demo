package dev.fmadrid.henrymeds.demo.controller.koto

import java.sql.Timestamp
import java.util.*


data class CreateAppointmentRequest(
        val startTime: Timestamp,
        val endTime: Timestamp,
        val scheduledBy: UUID,
        val requestedProvider: UUID
)