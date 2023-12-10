package dev.fmadrid.henrymeds.demo.controller

import dev.fmadrid.henrymeds.demo.controller.koto.CreateAppointmentRequest
import dev.fmadrid.henrymeds.demo.repository.domain.Appointment
import dev.fmadrid.henrymeds.demo.service.AppointmentService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/appointments"])
class AppointmentController(
        val appointmentService: AppointmentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody appointmentRequest: CreateAppointmentRequest) = appointmentService.create(appointmentRequest)

    @GetMapping("/client")
    fun getAllForClient(@RequestParam id: UUID, pageable: Pageable) = appointmentService.getAllByScheduledById(id, pageable)

    @GetMapping("/provider")
    fun getAllForProvider(@RequestParam id: UUID, pageable: Pageable) = appointmentService.getAllByRequestedProviderId(id, pageable)

    @PostMapping("/confirm")
    fun confirmAppointment(@RequestBody id: UUID): Appointment = appointmentService.confirmAppointment(id)
}