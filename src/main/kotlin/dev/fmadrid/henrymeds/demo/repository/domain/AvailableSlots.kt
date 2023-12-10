package dev.fmadrid.henrymeds.demo.repository.domain

import java.sql.Timestamp

class AvailableSlots(
        val startTime: Timestamp,
        val endTime: Timestamp,
        val provider: String
)
