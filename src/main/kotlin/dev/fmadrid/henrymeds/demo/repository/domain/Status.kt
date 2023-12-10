package dev.fmadrid.henrymeds.demo.repository.domain

enum class Status {
    OPEN, // Implies that a spot on the schedule is open
    EXPIRED, //TODO implement scheduler that releases >30 reservations
    CANCELLED, // TODO implement endpoint to cancel a reservation
    CLOSED, // This is a theoretical. If an appointment is confirmed and kept, provider can "close it" when complete
    CONFIRMED // Appointment has been confirmed
}