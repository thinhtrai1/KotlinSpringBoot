package com.example.blog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
    fun findAllByNameContainingIgnoreCase(name: String): Iterable<Product>
    fun findTop5ByOrderByAddedAtDesc(): Iterable<Product>
    fun findTop10ByOrderByAddedAtDesc(): Iterable<Product>
    fun findAll(pageable: Pageable): Page<Product>
}

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}