package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

class Response<T>(val data: T? = null, val message: String? = null)

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

    @GetMapping("")
    fun findAll(@RequestParam("search") search: String) = Response(repository.findAllByNameContaining(search))

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long) = repository.findById(id).run {
        if (isPresent) {
            Response(get())
        } else {
            Response(message = "Product not found")
        }
    }
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
    fun getHome() = Response(Home(
            repository.findAll(),
            repository.findAllByOrderByAddedAtDesc(),
            repository.findAll(),
    ))
}