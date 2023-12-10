package dev.fmadrid.henrymeds.demo.repository

import dev.fmadrid.henrymeds.demo.repository.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.*

@Repository
interface ProviderRepository : JpaRepository<Provider, UUID>