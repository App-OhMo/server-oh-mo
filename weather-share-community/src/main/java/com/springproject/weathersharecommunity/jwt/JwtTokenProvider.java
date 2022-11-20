package com.springproject.weathersharecommunity.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "thisissecretkey";

    private long tokenValidTime = 1000L * 60 * 60 * 24 * 60;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출출
   private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // header에서 토큰 가져오는 것
    public String resolveToken(HttpServletRequest request) {
        try {
            String token = null;
            Cookie cookie = WebUtils.getCookie(request, "X-AUTH-TOKEN");
            if (cookie != null)
                token = request.getHeader("X-AUTH-TOKEN");
            return token;
        } catch (Exception e) {
            return "false";
        }
    }
    public boolean validateToken(String jwtToken, HttpServletRequest request){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT signature");
            return false;
        } catch(UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token");
            return false;
        } catch(IllegalArgumentException e) {
            System.out.println("JWT token is invalid");
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("index error");
            return false;
        }
    }
}
