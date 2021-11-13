import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*
import javax.servlet.http.HttpServletRequest

fun LocalDateTime.format() = this.format(englishDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

private val englishDateFormatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd")
        .appendLiteral(" ")
        .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
        .appendLiteral(" ")
        .appendPattern("yyyy")
        .toFormatter(Locale.ENGLISH)

private fun getOrdinal(n: Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else -> "${n}th"
}

fun String.toSlug() = toLowerCase()
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-")

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