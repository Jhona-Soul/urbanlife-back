package com.urbanlife.urbanlife.security;

import com.urbanlife.urbanlife.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.urbanlife.urbanlife.models.usuario.Permission.*;
import static com.urbanlife.urbanlife.models.usuario.Permission.ADMIN_DELETE;
import static com.urbanlife.urbanlife.models.usuario.RolUser.ADMIN;
import static com.urbanlife.urbanlife.models.usuario.RolUser.CLIENTE;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/imagenes/{id}/producto-image",
                        "/productos/listaproductos-all",
                        "/productos/listaproductos-aleatorio",
                        "/productos/obtener/{id}",
                        "/api/v1/home/**",
                        "/categorias/listarcategorias-all",
                        "/categorias/{id}/categoria-image",
                        "/talles/listartalles-all",
                        "/categorias/{id}"
                )
                .permitAll()

                .requestMatchers("/productos/**").hasRole(ADMIN.name())
                .requestMatchers(POST, "/productos/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/productos/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/productos/**").hasAuthority(ADMIN_DELETE.name())

                .requestMatchers("/categorias/**").hasRole(ADMIN.name())
                .requestMatchers(POST, "/categorias/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/categorias/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/categorias/**").hasAuthority(ADMIN_DELETE.name())

                .requestMatchers("/talles/**").hasRole(ADMIN.name())
                .requestMatchers(POST, "/talles/**").hasAuthority(ADMIN_CREATE.name())

                .requestMatchers("/auth/usuarios/**").hasAnyRole(CLIENTE.name(), ADMIN.name())
                .requestMatchers(GET, "/auth/usuarios/**").hasAuthority(CLIENTE_READ.name())
                .requestMatchers(POST, "/auth/usuarios/**").hasAnyAuthority(ADMIN_CREATE.name(), CLIENTE_CREATE.name())
                //----Mejorar seguridad--------------------------------
                .requestMatchers(GET, "/obtener/{id}").hasAuthority(CLIENTE_READ.name())
                //----Volnurabilidad al ingresar el ID--------------------------------

                .requestMatchers(GET, "/listausuarios-all").hasAuthority(ADMIN_READ.name())
                .requestMatchers(GET, "/listareservas-all").hasAuthority(ADMIN_READ.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
