package com.example.blog

import generateAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
class HomeController(private val repository: ProductRepository, private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun findAll() = Response(userRepository.findAll())
}

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

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
    fun getForYou(@PathVariable page: Int) = repository.findAll(PageRequest.of(page, 10)).run {
        ResponseList(content, isLoadMore = page < totalPages - 1)
    }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/register")
    fun register(
            @Param("username") username: String,
            @Param("password") password: String,
            @Param("email") email: String,
            @Param("firstname") firstname: String,
            @Param("lastname") lastname: String
    ): Response<UserResponse> {
        if (repository.existsByUsername(username)) {
            return Response(null, "Username is already taken!")
        }
        if (repository.existsByEmail(email)) {
            return Response(null, "Email is already taken!")
        }
        val user = repository.save(User(username, passwordEncoder.encode(password), email, firstname, lastname, "USER"))
        return Response(
                UserResponse(
                        user.id!!,
                        username,
                        email,
                        firstname,
                        lastname,
                        generateAuthentication(username)
                )
        )
    }

    @PostMapping("/login")
    fun login(@Param("username") username: String, @Param("password") password: String): Response<UserResponse> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().authentication = auth
        (auth.principal as CustomUserDetail).user.let { user ->
            return Response(
                    UserResponse(
                            user.id!!,
                            username,
                            password,
                            user.firstname,
                            user.lastname,
                            generateAuthentication(username)
                    )
            )
        }
    }
}