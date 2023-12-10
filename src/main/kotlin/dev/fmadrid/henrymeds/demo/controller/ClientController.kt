package dev.fmadrid.henrymeds.demo.controller

import dev.fmadrid.henrymeds.demo.repository.ClientRepository
import dev.fmadrid.henrymeds.demo.repository.domain.Client
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/clients")
class ClientController(
        val clientRepository: ClientRepository
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody client: Client): Client = clientRepository.save(client)

    @GetMapping
    fun getAll(pageable: Pageable) = clientRepository.findAll(pageable)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID) = clientRepository.findById(id)


}