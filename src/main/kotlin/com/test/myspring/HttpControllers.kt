package com.test.myspring

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

private val OBJECT_MAPPER = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

@RestController
@RequestMapping("/api/user")
class UserController(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
) {

    init {
        repository.save(
            User(
                username = "thinhtrai1",
                password = passwordEncoder.encode("1"),
                email = "ducthinhtrai@gmail.com",
                firstname = "Thinh",
                lastname = "Duck",
                role = "USER",
            ),
        )
    }

    @PostMapping("/register")
    fun register(
        @Param("username") username: String,
        @Param("password") password: String,
        @Param("email") email: String,
        @Param("firstname") firstname: String,
        @Param("lastname") lastname: String,
    ): ResponseEntity<*> {
        if (repository.existsByUsername(username)) {
            error("Username is already taken!")
        }
        if (repository.existsByEmail(email)) {
            error("Email is already taken!")
        }
        val user = repository.save(User(username, passwordEncoder.encode(password), email, firstname, lastname, "USER"))
        return UserResponse(
            user.id!!,
            username,
            email,
            firstname,
            lastname,
            generateAuthentication(username)
        ).ok()
    }

    @PostMapping("/login")
    fun login(@Param("username") username: String, @Param("password") password: String): ResponseEntity<*> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().authentication = auth
        val user = (auth.principal as CustomUserDetail).user
        return UserResponse(
            user.id!!,
            username,
            user.email,
            user.firstname,
            user.lastname,
            generateAuthentication(username)
        ).ok()
    }

    @PostMapping("/contact")
    fun contact(
        @Param("name") name: String,
        @Param("email") email: String,
        @Param("message") message: String,
    ): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI("/")).build()
    }
}

@RestController
@RequestMapping("/api")
class HomeController(private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun findAll() = userRepository.findAll().ok()
}

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

    @GetMapping("")
    fun getProduct(
        @RequestParam("search") search: String? = null,
        @RequestParam("id") id: Long? = null,
    ) = if (search != null) {
        repository.findAllByNameContainingIgnoreCase(search).ok()
    } else if (id != null) {
        repository.findById(id).run {
            if (isPresent) {
                get().ok()
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
            }
        }
    } else {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing request param")
    }

    @GetMapping("/{page}")
    fun getProducts(
        @PathVariable page: Int,
        @RequestParam("ids") ids: List<Long>? = null,
    ) = if (ids != null) {
        repository.findByIds(ids, PageRequest.of(page, 10)).ok()
    } else {
        repository.findAll(PageRequest.of(page, 10)).ok()
    }
}

@RestController
@RequestMapping("/api/peoples")
class PeopleController(private val repository: PeopleRepository) {

    @GetMapping("")
    fun getPeoples() = repository.findAll().ok()

    @GetMapping("/{page}/{size}")
    fun getPeoples(
        @PathVariable page: Int,
        @PathVariable size: Int,
    ) = repository.findAll(PageRequest.of(page, size)).ok()
}

@RestController
@RequestMapping("/purchase")
class PurchaseController(private val repository: PurchaseRepository) {

    @PostMapping("/webhook/premium")
    fun receivePremiumWebhook(@RequestBody payload: Map<String?, Any?>?): String {
        if (payload?.containsKey("message") == true) {
            try {
                val message = payload["message"] as Map<*, *>
                val bytes = Base64.getDecoder().decode(message["data"] as String?)
                val data = OBJECT_MAPPER.readValue(bytes, Purchase::class.java)
                repository.save(data)
            } catch (e: Exception) {
                repository.saveError(payload)
            }
        }

        return "OK"
    }

    @GetMapping("/histories")
    fun getHistories() = repository.findAll().ok()

    @GetMapping("/errors")
    fun getErrors() = repository.getErrors().ok()
}

@RestController
@RequestMapping("/share")
class DeeplinkController {

    @GetMapping("/{id}")
    fun openApp(@PathVariable id: String): ResponseEntity<*> {
        val host = ServletUriComponentsBuilder.fromCurrentRequestUri().build().host
        val appPackage = "com.app.caror.caror"
        val playStoreLink = "https://play.google.com/store/apps/details?id=$appPackage&pid=$id"
        val html = ("<html><head><style>"
            + "body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f4f4f4; }"
            + "button { padding: 15px 30px; font-size: 18px; font-weight: bold; color: white; background: linear-gradient(45deg, #000000, #666666); border: none; border-radius: 8px; cursor: pointer; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); transition: 0.3s; }"
            + "button:hover { transform: scale(1.05); opacity: 0.9; }"
            + "</style><script>"
            + "function openApp() {"
            + "  var appLink = 'intent://$host/product?pid=$id#Intent;scheme=https;package=$appPackage;end;';"
            + "  window.location = appLink;"
            + "  setTimeout(function() { window.location = '" + playStoreLink + "'; }, 2000);"
            + "}"
            + "window.onload = openApp;"
            + "</script></head><body>"
            + "<button onclick=openApp>Go to the app</button>"
            + "</body></html>")

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html)
    }
}