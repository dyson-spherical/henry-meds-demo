package dev.fmadrid.henrymeds.demo.repository.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
data class Client(
        @Id val id: UUID? = UUID.randomUUID(),
        val name: String,
        @JsonIgnore
        @OneToMany(targetEntity = Appointment::class)
        val appointments: MutableList<Appointment>? = mutableListOf()
)