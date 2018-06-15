package com.feng.accounts.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collection;
import java.util.Map;

@Configuration
public class JwtAccessTokenConverterConfig implements JwtAccessTokenConverterConfigurer {

//    private static final String TENANT_ID = "tenantId";
//    private static final String TENANT_TYPE = "tenantType";
//    private static final String TENANT_STATUS = "tenantStatus";

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
            @Override
            public Authentication extractAuthentication(Map<String, ?> map) {
                Authentication authentication = super.extractAuthentication(map);
                if (authentication != null) {
                    CustomUser principal = getPrincipal(authentication.getName(), authentication.getAuthorities(), map);
                    if (principal != null) {
                        return new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), authentication.getAuthorities());
                    }
                }
                return null;
            }

            //从令牌中取出用户信息，转换为jwtToken
            private CustomUser getPrincipal(String username, Collection<? extends GrantedAuthority> authorities, Map<String, ?> map) {
//                if (map.containsKey(TENANT_ID) && map.containsKey(TENANT_TYPE) && map.containsKey(TENANT_STATUS)) {
//                    String tenantId = (String) map.get(TENANT_ID);
//                    String tenantType = (String) map.get(TENANT_TYPE);
//                    String tenantStatus = (String) map.get(TENANT_STATUS);
//                    return new CustomUser(username, "N/A", authorities, tenantId, tenantType, tenantStatus);
//                }
                return new CustomUser(username, "N/A", authorities);
            }
        });
        converter.setAccessTokenConverter(accessTokenConverter);
    }
}
