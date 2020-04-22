package com.satyy.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/health")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    /**
     * Another way to configure resource server token service with its client id, secret and authorization server check token endpoint.
     * Using these information resource server will validate all the incoming request token.
     * Currently we are setting these info directly from application.yml.
     * This can be useful while reading client id / secret from kubernetes secrets mounted to the pod.
     *
     * To Use this, uncomment below bean configuration & comment out the application.yml configuration for spring oauth2 resource configs.
     */

   /* @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("RESOURCE_SERVER");
        tokenServices.setClientSecret("resource_server_password");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8888/oauth/check_token");
        return tokenServices;
    }*/
}
