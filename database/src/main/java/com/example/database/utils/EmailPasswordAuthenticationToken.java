package com.example.database.utils;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class EmailPasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    public EmailPasswordAuthenticationToken(Object email, Object password) {
        super(null);
        this.principal = email;
        this.credentials = password;
        setAuthenticated(false);
    }

    public EmailPasswordAuthenticationToken(Object email, Object password, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = email;
        this.credentials = password;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
