import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest

const val HEADER_STRING = "Authorization"
const val SECRET = "HO_LO_SECRET"
const val TOKEN_PREFIX = "Bearer "

fun generateAuthentication(username: String) = TOKEN_PREFIX + Jwts.builder()
        .setSubject(username)
        .setExpiration(Date(System.currentTimeMillis() + 864_000_000))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact()!!

fun getAuthentication(request: HttpServletRequest?): Authentication? {
    request?.getHeader(HEADER_STRING)?.let {
        val username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(it.replace(TOKEN_PREFIX, "")).body.subject
        return UsernamePasswordAuthenticationToken(username, null, emptyList())
    }
    return null
}