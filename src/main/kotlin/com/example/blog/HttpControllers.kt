package com.example.blog

import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

class Response<T>(val data: T? = null, val message: String? = null)

@RestController
@RequestMapping("/api")
class HomeController(private val repository: ProductRepository) {

    @GetMapping("/getHome")
    fun getHome() = Response(Home(
            repository.findTop5ByOrderByAddedAtDesc(),
            repository.findTop5ByOrderByAddedAtDesc(),
            repository.findTop10ByOrderByAddedAtDesc(),
    ))

    @GetMapping("/insert")
    fun insert(): Response<Product> {
        val product = when ((0..2).random()) {
            0 -> repository.save(Product(
                    name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
                    price = 4600000000,
                    thumbnail = "https://ducthinhtrai.000webhostapp.com/images/VinFastPresident2021.jpg",
                    rate = 4.5f,
                    shopId = 1,
                    shopName = "President Đà Nẵng"))
            1 -> repository.save(Product(
                    name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
                    price = 1835693000,
                    thumbnail = "https://ducthinhtrai.000webhostapp.com/images/VinFast-Lux-SA2.0.png",
                    rate = 5f,
                    shopId = 2,
                    shopName = "WorldCar"))
            else -> repository.save(Product(
                    name = "VinFast Fadil - Phong cách châu Âu",
                    price = 449100000,
                    thumbnail = "https://vinfastdanang.com.vn/wp-content/uploads/2020/06/Fadil_Red.jpg",
                    rate = 5f,
                    shopId = 2,
                    shopName = "WorldCar"))
        }
        return Response(product)
    }
}

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

    @GetMapping("")
    fun findAll(
            @RequestParam("search") search: String? = null,
            @RequestParam("id") id: Long? = null
    ) = if (search != null) {
        Response(repository.findAllByNameContainingIgnoreCase(search))
    } else if (id != null) {
        repository.findById(id).run {
            if (isPresent) {
                Response(get())
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
            }
        }
    } else {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Missing RequestParam")
    }

    @GetMapping("/{page}")
    fun getForYou(@PathVariable page: Int) = Response(repository.findAll(PageRequest.of(page, 10)))
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) =
            repository.findByLogin(login) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}