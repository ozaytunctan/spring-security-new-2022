package com.ozaytunctan.security.manager;

import com.ozaytunctan.constants.Constants;
import com.ozaytunctan.security.model.UserDetailsWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class JwtTokenManager implements TokenGenerator {


    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private static final String AUTH_HEADER_KEY = HttpHeaders.AUTHORIZATION;

    private static final String secretKey = "OZAY875656456465464646DFR4HBFD4H56DS4H56DS45DSHDS5H4DS54H5556DSVDS5VSDVS14DSVD154S6VD14";

    private static final Long expirationDurationInMs = 1 * 2 * 60 * 60 * 1000L;

    private static final String ROLES_KEY = "roles";

    private final EncryptionManager encryptionManager;

    public JwtTokenManager(EncryptionManager encryptionManager) {
        this.encryptionManager = encryptionManager;
    }


    @Override
    public String generate(UserDetails user, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        Collection<String> roles = ((UserDetailsWrapper) user).getRoles();
//        if (roles.contains(new SimpleGrantedAuthority("ADMIN"))) {
//            claims.put("isAdmin", true);
//        }
//
//        if (roles.contains(new SimpleGrantedAuthority("USER"))) {
//            claims.put("isUser", true);
//        }
//
//        if (userId != null && userId > 0) {
//            claims.put("ticket", encryptionManager.encrypt(String.valueOf(userId)));
//        }

        claims.put(ROLES_KEY, roles);
        //ID   ATAĞINI ÖNLEMEK İÇİN EKLENDİ..
        claims.put(Constants.TICKET, encryptionManager.encrypt(userId + ""));
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()//
                .setClaims(claims)//
                .setSubject(subject)//
                .setIssuedAt(new Date(currentTimeMillis))//
                .setExpiration(new Date(currentTimeMillis + expirationDurationInMs))//
                .signWith(getSecretKey()).compact();


    }

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // retrieve ticket from jwt token
    public String getTicketFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(Constants.TICKET, String.class);
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {

        Claims claims = getAllClaimsFromToken(token);
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        List<String> roles = claims.get(ROLES_KEY, List.class);

        if (roles != null && !roles.isEmpty()) {
            authorities = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
        }

//        Boolean admin = claims.get("isAdmin", Boolean.class);
//        if (admin != null && admin) {
//            roles.add(new SimpleGrantedAuthority("ADMIN"));
//        }
//
//        Boolean user = claims.get("isUser", Boolean.class);
//        if (user != null && user) {
//            roles.add(new SimpleGrantedAuthority("USER"));
//        }

        return authorities;

    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)//
                .getBody();

    }


    public String getToken(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader(AUTH_HEADER_KEY);
        if (requestTokenHeader != null && requestTokenHeader.startsWith(AUTH_HEADER_PREFIX))
            return requestTokenHeader.substring(AUTH_HEADER_PREFIX.length());

        return resolveCookieValue(request);
    }

    private String resolveCookieValue(HttpServletRequest request) {

        List<Cookie> cookies = request.getCookies() != null ? Arrays.asList(request.getCookies()) : new ArrayList<>();
        Optional<Cookie> sessionCookie = cookies.stream()
                .filter(c -> c != null && c.getName().equals(Constants.SESSION_ID)).findFirst();

        if (sessionCookie.isPresent()) {
            return sessionCookie.get().getValue();
        }

        return null;
    }
    @Override
    public boolean isValid(String token) {
        final String username = getUsernameFromToken(token);
        return (username != null && !isTokenExpired(token));
    }

    // check if the token has expired
    @Override
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


}
