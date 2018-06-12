package com.feng.authserver.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUser) {
            CustomUser customUser = (CustomUser) principal;
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                    ImmutableMap.of(
                            "tenantId", customUser.getTenantId(),
                            "tenantType", customUser.getTenantType(),
                            "tenantStatus", customUser.getTenantStatus()
                    ));
        }
        return accessToken;
    }
}
