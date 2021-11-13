package com.example.blog

import getAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthTokenFilter: OncePerRequestFilter() {
    @Autowired
    private lateinit var userDetailsService: CustomUserDetailService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val user = getAuthentication(request) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong token")
        val userDetail = userDetailsService.loadUserByUsername(user.principal as String)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities)
        filterChain.doFilter(request, response)
    }
}