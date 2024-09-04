package yerong.wedle.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import yerong.wedle.oauth.handler.CustomAccessDeniedHandler;
import yerong.wedle.oauth.jwt.CustomAuthenticationEntryPoint;
import yerong.wedle.oauth.jwt.JwtAuthenticationFilter;
import yerong.wedle.oauth.jwt.JwtProvider;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/swagger"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                                        AntPathRequestMatcher.antMatcher("/swagger*/**"),
                                        AntPathRequestMatcher.antMatcher("/api-docs"),
                                        AntPathRequestMatcher.antMatcher("/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/v3/**"),
                                        AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/login/apple"),
                                        AntPathRequestMatcher.antMatcher("/login/refresh")


                                ).permitAll()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS)
                                )
                                .permitAll()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/api/**")
                                ).authenticated().anyRequest().permitAll()
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
