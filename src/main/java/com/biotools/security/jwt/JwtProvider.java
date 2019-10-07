package com.biotools.security.jwt;

import com.biotools.ds.UserService;
import com.biotools.entity.User;
import com.biotools.message.response.JwtResponse;
import com.biotools.security.services.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtProvider {
 
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
 
    @Value("${biotools.app.jwtSecret}")
    private String JWT_SECRET;

    @Value("${biotools.app.jwtExpiration}")
    private int JWT_EXPIRATION;

    @Value("${biotools.app.jwtRefresh}")
    private int JWT_REFRESH;

    @Autowired
    private UserService userService;

    public JwtResponse generateJwtToken(Authentication authentication) {
 
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        String token = createToken(userPrincipal);

        String refreshToken = createRefreshToken(userPrincipal);

        return new JwtResponse(token, refreshToken, userPrincipal.getUsername(), userPrincipal.getAuthorities());
    }

    private String createToken(UserPrinciple user) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .setClaims(buildUserClaims(user))
                .setExpiration(getTokenExpirationDate(false))
                .setIssuedAt(new Date())
                .compact();
    }

    public String createRefreshToken(UserPrinciple user) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .setClaims(buildUserClaims(user))
                .setExpiration(getTokenExpirationDate(true))
                .setIssuedAt(new Date())
                .compact();
    }

    public String refreshJwtToken(String refreshToken) {
        Jws<Claims> claims = validateJwtRefreshToken(refreshToken);
        return createTokenFromClaims(claims);
    }

    private String createTokenFromClaims(Jws<Claims> jws) {

        return Jwts.builder()
                .setClaims(jws.getBody())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .setExpiration(getTokenExpirationDate(false))
                .setIssuedAt(new Date())
                .compact();
    }

    private Jws<Claims> validateJwtRefreshToken(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(JWT_SECRET);
        Jws<Claims> claims = parser.parseClaimsJws(token);

        User user = userService.findUserByUsername(claims.getBody().getSubject());

        return parser.require(JWT_SECRET, user.getUserSecret()).parseClaimsJws(token);
    }

    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                      .setSigningKey(JWT_SECRET)
                      .parseClaimsJws(token)
                      .getBody().getSubject();
    }

    private Date getTokenExpirationDate(boolean refreshToken) {
        Calendar calendar = Calendar.getInstance();

        if(refreshToken) {
            calendar.add(Calendar.MONTH, JWT_REFRESH);
        } else {
            calendar.add(Calendar.MINUTE, JWT_EXPIRATION);
        }

        return calendar.getTime();
    }

    private Claims buildUserClaims(UserPrinciple user) {
        Claims claims = new DefaultClaims();

        claims.setSubject(String.valueOf(user.getUsername()));
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", String.join(",", AuthorityUtils.authorityListToSet(user.getAuthorities())));
        claims.put(JWT_SECRET, user.getUserSecret());

        return claims;
    }
}