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
 * Class with all the security config implemented.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/** Inject LogoutConfig class. */
    @Autowired
    private LogoutConfig logoutConfig;
    
    /**
     * Method securityFilterChain.
     * 
     * This method define the security of the distinct controllers that the app have.
     * 
     * @param httpSecurity - default
     * @return httpSecurity with the current configuration.
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/resetPassword*", "/send*", "/anonymous/*", "/register/*").permitAll()
					.requestMatchers("/json/*").hasAuthority("ADMIN")
					.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.failureUrl("/loginError")
						.defaultSuccessUrl("/", true)
						.permitAll()
						)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessHandler(logoutConfig)
						)
				.build();
	}
    
    /**
     * Method usersCustom.
     * 
     * This method define how the login have to check the username, password or profile in the custom database.
     * 
     * @param dataSource
     * @return users - UserDetails with the information of email, password, status and profile.
     * 
     */
    @Bean
    UserDetailsManager usersCustom(DataSource dataSource) {
    	
    	JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    	
    	users.setUsersByUsernameQuery("select email, password, status from Users u where email=?");
    	users.setAuthoritiesByUsernameQuery("select email, profile from Profiles p where email=?");
        	   	
    	return users;
    	
    }
    
    /**
     * Method passwordEncoder.
     * 
     * Define how the password is stored in the database.
     * 
     * @return BCryptPasswordEncoder - the "plain text password" encoded.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

}

