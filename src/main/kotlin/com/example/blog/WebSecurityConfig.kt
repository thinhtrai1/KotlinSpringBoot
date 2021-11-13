package com.example.blog

import getAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
//        prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userDetailsService: CustomUserDetailService

    @Autowired
    private lateinit var unauthorizedHandler: AuthEntryPointJwt

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
//                .addFilterBefore(object : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/user/login"), authenticationManager()) {
//                    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
//                        val username = request.getParameter("username")
//                        val password = request.getParameter("password")
//                        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
//                    }
//
//                    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication) {
//                        response?.addHeader(HEADER_STRING, generateAuthentication(authResult.name))
//                    }
//                }, UsernamePasswordAuthenticationFilter::class.java)
//                .addFilterBefore(object : GenericFilterBean() {
//                    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
//                        SecurityContextHolder.getContext().authentication = getAuthentication(request as? HttpServletRequest)
//                        chain?.doFilter(request, response)
//                    }
//                }, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth?.inMemoryAuthentication()?.withUser(
//                withUsername("thinhtrai3").password(passwordEncoder().encode("123")).roles("USER").build()
//        )
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

//    @Bean
//    fun authenticationProvider() : DaoAuthenticationProvider {
//        return DaoAuthenticationProvider().apply {
//            setUserDetailsService(userDetailsService())
//            setPasswordEncoder(passwordEncoder())
//        }
//    }
//
//    @Bean
//    override fun userDetailsService(): UserDetailsService {
//        return CustomUserDetailService()
//    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder  {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }
}