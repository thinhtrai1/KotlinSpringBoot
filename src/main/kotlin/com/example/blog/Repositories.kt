package com.example.blog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
    fun findAllByNameContainingIgnoreCase(name: String): Iterable<Product>
    fun findTop5ByOrderByAddedAtDesc(): Iterable<Product>
    fun findTop10ByOrderByAddedAtDesc(): Iterable<Product>
    fun findAll(pageable: Pageable): Page<Product>
}

interface PeopleRepository : CrudRepository<People, Long> {
    fun findAll(pageable: Pageable): Page<People>
}

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String ): User?
    fun existsByUsername(username: String ): Boolean
    fun existsByEmail(email: String ): Boolean
}