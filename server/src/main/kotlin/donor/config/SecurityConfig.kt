package donor.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
//    private val authenticationProvider: AuthenticationProvider
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        tokenAuthenticationFilter: TokenAuthenticationFilter
//        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/**")
                    .permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/register-account", "/api/login")
//                    .permitAll()
//                    .requestMatchers("/api/user**")
//                    .hasRole("ADMIN")
//                    .anyRequest()
//                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
//            .authenticationProvider(authenticationProvider)
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .exceptionHandling().authenticationEntryPoint(authenticationErrorHandler)
        return http.build()
    }
}