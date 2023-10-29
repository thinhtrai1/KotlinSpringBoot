import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest

const val HEADER_STRING = "Authorization"
const val SECRET = "HO_LO_SECRET"

fun generateAuthentication(username: String) = Jwts.builder()
        .setSubject(username)
        .setExpiration(Date(System.currentTimeMillis() + 864_000_000))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact()!!

fun getAuthentication(request: HttpServletRequest?): Authentication? {
    request?.getHeader(HEADER_STRING)?.let {
        val username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(it.replace("Bearer ", "")).body.subject
        return UsernamePasswordAuthenticationToken(username, it, emptyList())
    }
    return null
}