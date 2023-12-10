package dev.fmadrid.henrymeds.demo.controller

import dev.fmadrid.henrymeds.demo.repository.ProviderRepository
import dev.fmadrid.henrymeds.demo.repository.domain.Provider
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/providers")
class ProviderController(
        val providerRepository: ProviderRepository
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody provider:Provider) = providerRepository.save(provider)


    @GetMapping
    fun getAllProviders(pageable: Pageable): Page<Provider> = providerRepository.findAll(pageable)

    @GetMapping("/{id}")
    fun getProvider(@PathVariable id: UUID) = providerRepository.findById(id)
}