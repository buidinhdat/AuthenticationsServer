package com.mobifone.crm.authentication.Configuration.Service;

import org.springframework.security.core.Authentication;

public interface AppAuthorizer {
    boolean authorize(Authentication authentication,String pathApi);
}
