package dev.fmadrid.henrymeds.demo.service

import dev.fmadrid.henrymeds.demo.controller.koto.CreateAppointmentRequest
import dev.fmadrid.henrymeds.demo.repository.AppointmentRepository
import dev.fmadrid.henrymeds.demo.repository.ClientRepository
import dev.fmadrid.henrymeds.demo.repository.ProviderRepository
import dev.fmadrid.henrymeds.demo.repository.domain.Appointment
import dev.fmadrid.henrymeds.demo.repository.domain.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class AppointmentService(
        private val appointmentRepository: AppointmentRepository,
        private val clientRepository: ClientRepository,
        private val providerRepository: ProviderRepository
) {
    fun create(appointmentRequest: CreateAppointmentRequest): Appointment {
        val scheduledBy = clientRepository.findById(appointmentRequest.scheduledBy)
        val requestedProvider = providerRepository.findById(appointmentRequest.requestedProvider)
        if (!scheduledBy.isPresent || !requestedProvider.isPresent) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Either scheduledBy or requestedProvider do not exist")
        }
        if (appointmentRequest.startTime.after(Timestamp.from(
                        LocalDateTime.now().minusDays(1L).toInstant(
                                ZoneId.systemDefault().rules.getOffset(LocalDateTime.now()))))) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only schedule appointments 24 hours in advance")
        }

        val client = scheduledBy.get()
        val provider = requestedProvider.get()
        val appointment = Appointment(
                UUID.randomUUID(),
                appointmentRequest.startTime,
                appointmentRequest.endTime,
                client,
                provider
        )
        appointmentRepository.save(appointment)
        client.appointments?.add(appointment)
        provider.appointments?.add(appointment)
        clientRepository.save(client)
        providerRepository.save(provider)

        return appointment
    }

    fun getAllByScheduledById(id: UUID, pageable: Pageable): Page<Appointment> =
            appointmentRepository.getAllByScheduledById(id, pageable)

    fun getAllByRequestedProviderId(id: UUID, pageable: Pageable): Page<Appointment> =
            appointmentRepository.getAllByRequestedProviderId(id, pageable)

    fun confirmAppointment(id: UUID): Appointment {
        val appointment = appointmentRepository.findById(id)
        if (appointment.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to confirm your appointment. We didn't find it!")
        val appt = appointment.get()
        val confirmed = Appointment(
                appt.id,
                appt.startTime,
                appt.endTime,
                appt.scheduledBy,
                appt.requestedProvider,
                Status.CONFIRMED
        )
        return confirmed
    }

}