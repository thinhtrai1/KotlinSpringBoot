package com.example.blog

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProductRepository : CrudRepository<Product, Long> {
    fun findAllByNameContainingIgnoreCase(name: String): Iterable<Product>
    fun findTop5ByOrderByAddedAtDesc(): Iterable<Product>
    fun findTop10ByOrderByAddedAtDesc(): Iterable<Product>
    fun findAll(pageable: Pageable): Page<Product>
}

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String ): User?
}