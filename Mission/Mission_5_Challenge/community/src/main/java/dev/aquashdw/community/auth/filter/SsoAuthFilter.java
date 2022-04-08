package dev.aquashdw.community.auth.filter;

import dev.aquashdw.community.auth.service.LogoutCacheService;
import dev.aquashdw.community.model.UserHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static dev.aquashdw.community.auth.LikelionSsoConsts.LIKELION_LOGIN_COOKIE;

@Component
public class SsoAuthFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SsoAuthFilter.class);
    private final WebClient authSsoWebclient;
    private final LogoutCacheService logoutCacheService;

    public SsoAuthFilter(
            WebClient authSsoWebclient,
            LogoutCacheService logoutCacheService
    ) {
        this.authSsoWebclient = authSsoWebclient;
        this.logoutCacheService = logoutCacheService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Optional<String> authToken = getAuthToken(httpRequest, httpResponse);

        if (authToken.isEmpty()) {
            this.setAnonymousAuthentication();
        } else {
            logger.debug("Login Token Value: {}", authToken.get());
            this.getUserHash(authToken.get()).ifPresentOrElse(
                    this::setSsoAuthentication,
                    this::setAnonymousAuthentication
            );
        }

        chain.doFilter(request, response);
    }

    private Optional<String> getAuthToken(HttpServletRequest request, HttpServletResponse response){
        Optional<String> authToken = request.getCookies() == null ?
                this.authTokenFromQuery(request, response) :
                this.authTokenFromCookie(request.getCookies())
                    .or(() -> this.authTokenFromQuery(request, response));
        if (authToken.isPresent() &&
                this.logoutCacheService.checkUserStatus(authToken.get())) return authToken;
        else return Optional.empty();
    }

    private Optional<String> authTokenFromCookie(Cookie[] cookies){
        String authToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LIKELION_LOGIN_COOKIE)) {
                logger.debug("found token in cookie");
                authToken = cookie.getValue();
                break;
            }
        }

        if (authToken == null) logger.debug("could not find token from cookie");
        return Optional.ofNullable(authToken);
    }

    private Optional<String> authTokenFromQuery(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        String queryString = httpRequest.getQueryString();
        if (queryString == null) {
            logger.debug("query string is null");
            return Optional.empty();
        }
        String[] queryParams = queryString.split("&");
        for (String queryParam: queryParams) {
            String[] queryParamSplit = queryParam.split("=");
            if (queryParamSplit.length == 1) continue;
            if (queryParamSplit[0].equals(LIKELION_LOGIN_COOKIE)) {
                logger.debug("found token in query");
                String loginToken = queryParamSplit[1];
                Cookie newTokenCookie = new Cookie(LIKELION_LOGIN_COOKIE, loginToken);
                newTokenCookie.setPath("/");
                httpResponse.addCookie(newTokenCookie);
                return Optional.of(queryParamSplit[1]);
            }
        }
        logger.debug("could not find token from query");
        return Optional.empty();
    }

    private Optional<UserHash> getUserHash(String tokenValue){
        ResponseEntity<UserHash> ssoResponseEntity = this.authSsoWebclient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("cookie-id", tokenValue)
                                .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND))
                                return Mono.empty();
                            return Mono.error(new ResponseStatusException(clientResponse.statusCode()));
                        })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                            logger.error("Received 5xx error from SSO: {}", clientResponse.statusCode());
                            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
                        })
                .toEntity(UserHash.class)
                .block();
        if (ssoResponseEntity == null || ssoResponseEntity.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ssoResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            return Optional.empty();
        }
        else return Optional.of(ssoResponseEntity.getBody());
    }

    private void setSsoAuthentication(UserHash userHash){
        SecurityContextHolder.getContext().setAuthentication(new SsoAuthentication(
                userHash.getUsername(),
                new User(userHash.getUsername(), userHash.getCookieId(), new ArrayList<>())
        ));
    }

    private void setAnonymousAuthentication (){
        logger.debug("Login Token Missing");
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken(
                        "anonymous",
                        "anonymous",
                        Collections.singletonList(
                                (GrantedAuthority) () -> "ROLE_ANONYMOUS"))
        );
    }

    private static class SsoAuthentication implements Authentication {
        private final String username;
        private final List<GrantedAuthority> grantedAuthorities;
        private final User principal;

        public SsoAuthentication(String username, User principal) {
            this.username = username;
            this.grantedAuthorities = new ArrayList<>();
            this.principal = principal;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.grantedAuthorities;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return this.username;
        }
    }
}
