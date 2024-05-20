package tfg.daniel.riskreal.riskrealApp.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
    @Autowired
    private LogoutConfig logoutConfig;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/resetPassword", "/register", "/register/done").permitAll()
					//.requestMatchers("/resetPassword").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.failureUrl("/loginError")
						.permitAll()
						)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessHandler(logoutConfig)
						)
				.build();
	}
    
    @Bean
    UserDetailsManager usersCustom(DataSource dataSource) {
    	
    	JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    	
    	users.setUsersByUsernameQuery("select email, password, status from Users u where email=?");
    	users.setAuthoritiesByUsernameQuery("select email, profile from Profiles p where email=?");
    	   	
    	return users;
    	
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}

