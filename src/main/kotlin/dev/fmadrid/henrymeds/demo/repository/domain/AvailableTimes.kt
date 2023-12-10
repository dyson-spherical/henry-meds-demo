package dev.fmadrid.henrymeds.demo.repository.domain

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(uniqueConstraints = [
    UniqueConstraint(name = "unique", columnNames = ["START_DATE", "END_DATE", "PROVIDER_ID"])
])
data class AvailableTimes(
        @Id val id: UUID,
        val startDate: Timestamp, // When this availability window opens up
        val endDate: Timestamp, // When this window closes
        @ManyToOne
        val provider: Provider,
        val status: Status
)
