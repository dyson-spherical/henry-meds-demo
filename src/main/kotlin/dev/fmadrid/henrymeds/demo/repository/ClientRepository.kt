package dev.fmadrid.henrymeds.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import dev.fmadrid.henrymeds.demo.repository.domain.Client

interface ClientRepository : JpaRepository<Client, UUID>