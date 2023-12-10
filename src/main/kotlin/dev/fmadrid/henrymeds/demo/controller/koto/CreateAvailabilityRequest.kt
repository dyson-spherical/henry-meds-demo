package dev.fmadrid.henrymeds.demo.controller.koto

import java.sql.Timestamp

data class CreateAvailabilityRequest(
        val startTime: Timestamp,
        val endTime: Timestamp
)
