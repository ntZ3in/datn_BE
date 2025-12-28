package bookcarupdate.bookcar.config;

import bookcarupdate.bookcar.models.Role;
import bookcarupdate.bookcar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/seller/**").permitAll()
//                        .requestMatchers("/api/v1/user/**").permitAll()
//                        .requestMatchers("/api/v1/payment/**").permitAll()
//                        .anyRequest().authenticated()
//                ).sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider()).addFilterBefore(
//                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
//                );
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(auth -> auth
//                        // PUBLIC: ok
//                        .requestMatchers("/api/v1/auth/**").permitAll()
//
//                        //dùng chung k cần tokken
//                        .requestMatchers(
//                                "/api/v1/common/get-all-product",
//                                "/api/v1/common/get-all-product-pagi/**",
//                                "/api/v1/common/get-product/**",
//                                "/api/v1/common/get-store/**",
//                                "/api/v1/common/search",
//                                "/api/v1/common/get-owner-name"
//                        ).permitAll()
//                        // dùng chung, cần tokken
//                        .requestMatchers("/api/v1/common/**").authenticated()
//
//                        // USER
//                        .requestMatchers("/api/v1/user/**").hasRole("USER")
//
//                        // SELLER
//                        .requestMatchers("/api/v1/seller/**").hasRole("SELLER")
//
//                        // PAYMENT
//                        .requestMatchers("/api/v1/payment/**").authenticated()
//
//                        // ADMIN
//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
//
//                        .anyRequest().authenticated()
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll())
//
//                        .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // PUBLIC
                    .requestMatchers("/api/v1/auth/**").permitAll()

                    // PUBLIC chat
                    .requestMatchers("/api/v1/chat").permitAll()

                    // COMMON – public
                    .requestMatchers(
                            "/api/v1/common/get-all-product",
                            "/api/v1/common/get-all-product-pagi/**",
                            "/api/v1/common/get-product/**",
                            "/api/v1/common/get-store/**",
                            "/api/v1/common/search",
                            "/api/v1/common/get-owner-name"
                    ).permitAll()

                    // COMMON – cần token
                    .requestMatchers("/api/v1/common/**").authenticated()

                    // USER
                    .requestMatchers("/api/v1/user/**").hasRole("USER")

                    // SELLER
                    .requestMatchers("/api/v1/seller/**").hasRole("SELLER")

                    // PAYMENT
                    .requestMatchers("/api/v1/payment/**").authenticated()

                    // ADMIN - tạm cho phép tất cả authenticated users (test)
                    .requestMatchers("/api/v1/admin/**").authenticated()

                    .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
