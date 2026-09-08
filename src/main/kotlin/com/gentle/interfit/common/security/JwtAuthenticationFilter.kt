package com.gentle.interfit.common.security

import com.gentle.interfit.auth.application.port.out.TokenProviderPort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenProviderPort: TokenProviderPort
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token != null
            && tokenProviderPort.validateToken(token)
            && tokenProviderPort.getTokenType(token) == "access"
        ) {
            val userId = tokenProviderPort.getUserId(token)
            val role = tokenProviderPort.getRole(token)
            val authorities = when (role) {
                "ADMIN" -> listOf(
                    SimpleGrantedAuthority("ROLE_ADMIN"),
                    SimpleGrantedAuthority("ROLE_USER")
                )

                "USER" -> listOf(
                    SimpleGrantedAuthority("ROLE_USER")
                )

                else -> emptyList()
            }

            val authentication = UsernamePasswordAuthenticationToken(
                userId,
                null,
                authorities
            )

            SecurityContextHolder
                .getContext()
                .authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(
        request: HttpServletRequest
    ): String? {
        val authorization = request.getHeader("Authorization")

        if (authorization == null ||
            !authorization.startsWith("Bearer ")
        ) {
            return null
        }

        return authorization.substring(7)
    }
}