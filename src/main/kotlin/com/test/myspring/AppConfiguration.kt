package com.test.myspring

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*

@Configuration
class AppConfiguration(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByUsername(username)?.let {
                CustomUserDetail(it)
            } ?: throw UsernameNotFoundException("Username does not exist")
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.getAuthenticationManager()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }
}

@Configuration
class SecurityConfiguration {

    @Autowired
    private lateinit var unauthorizedHandler: AuthEntryPointJwt

    @Autowired
    private lateinit var authenticationProvider: AuthenticationProvider

    @Autowired
    private lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .exceptionHandling {
                it.authenticationEntryPoint(unauthorizedHandler)
            }
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers(
                        "/*",
                        "/api/product/**",
                        "/api/user/**",
                        "/images/**",
                        "/purchase/**",
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}

class CustomUserDetail(val user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val username = getAuthenticationUsername(request)
        if (username != null) {
            val userDetail = userDetailsService.loadUserByUsername(username)
            val auth = UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
            auth.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }
}

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
//        if (authException is BadCredentialsException) {
//            response?.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Wrong password")
//        } else {
//            response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
//        }

//        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream?.also {
            ObjectMapper().writeValue(
                it,
                ErrorResponse(
                    code = HttpStatus.UNAUTHORIZED.value().toString(),
                    message = HttpStatus.UNAUTHORIZED.reasonPhrase,
                ),
            )
        }?.flush()
    }
}

private val SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode("Y29tLnRlc3QubXlzcHJpbmcuTVlfU1BSSU5HX1NFQ1JFVF9LRVk="))

fun generateAuthentication(username: String): String {
    return Jwts.builder()
        .subject(username)
        .expiration(Date(System.currentTimeMillis() + 864_000_000))
        .signWith(SECRET)
        .compact()
}

fun getAuthenticationUsername(request: HttpServletRequest?): String? {
    return try {
        request?.getHeader("Authorization")?.let {
            Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(it.replace("Bearer ", ""))
                .payload.subject
        }
    } catch (e: Exception) {
        null
    }
}

@Configuration
class MvcConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        arrayOf(
            "/privacy",
            "/contact",
        ).forEach {
            registry.addViewController(it).setViewName("forward:$it.html")
        }
    }
}