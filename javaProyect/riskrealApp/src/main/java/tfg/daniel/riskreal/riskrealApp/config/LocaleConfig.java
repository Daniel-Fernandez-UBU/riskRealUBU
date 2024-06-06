package tfg.daniel.riskreal.riskrealApp.config;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


/**
 * Class LocaleConfig.
 * 
 * Class that implements MvcConfigurer to define some aditional config about the locale session.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer{
	
	

	/**
	 * Method localeResolver.
	 * 
	 * Define the default locale session.
	 * 
	 * @return slr - SessionLocaleResolver
	 */
	@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("es"));
		slr.setLocaleAttributeName("session.current.locale");
		slr.setTimeZoneAttributeName("session.current.timezone");
		return slr;
	}
	
	/**
	 * Method localeChangeInterceptor.
	 * 
	 * Defines a new locale change interceptor with the param "lang"
	 * 
	 * @return lci - localeChangeInterceptor
	 */
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	/**
	 * Method addInterceptor.
	 * 
	 * Override the default addInterceptors method with the custom configuration.
	 * 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	
}
