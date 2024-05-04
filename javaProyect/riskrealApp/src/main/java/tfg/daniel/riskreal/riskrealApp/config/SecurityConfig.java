package tfg.daniel.riskreal.riskrealApp.config;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig.
 * 
 * Class with all the security config related
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.permitAll()
						)
				.logout(logout -> logout
						.permitAll()
						.logoutSuccessUrl("/")
						)
				.build();
	}
    
    @Bean
    UserDetailsManager usersCustom(DataSource dataSource) {
    	
    	JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    	
    	users.setUsersByUsernameQuery("select username, password, status from Users u where username=?");
    	users.setAuthoritiesByUsernameQuery("select username, profile from Profiles p where username=?");
    	   	
    	return users;
    	
    }

}

