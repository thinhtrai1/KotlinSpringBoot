package com.example.blog

import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
    fun findAllByNameContainingIgnoreCase(name: String): Iterable<Product>
    fun findAllByOrderByAddedAtDesc(): Iterable<Product>
}

interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
}