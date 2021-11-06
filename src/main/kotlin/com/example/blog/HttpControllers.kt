package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

class Response<T>(val data: T, val message: String? = null)

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

    @GetMapping("")
    fun findAll() = repository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{name}")
    fun findAll(@PathVariable name: String) = repository.findByName(name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Not exist")
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("")
    fun findAll() = repository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
            repository.findByLogin(login) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}

@RestController
@RequestMapping("/api/getHome")
class HomeController(private val repository: ProductRepository) {

    @GetMapping("")
    fun getAll() = Response(repository.findAll())
}