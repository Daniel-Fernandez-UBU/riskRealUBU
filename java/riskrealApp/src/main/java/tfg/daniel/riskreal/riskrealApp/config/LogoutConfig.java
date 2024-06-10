package tfg.daniel.riskreal.riskrealApp.config;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Class LogoutCofing.
 * 
 * Class that override the logout config to get the lang info related.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
@Component
public class LogoutConfig implements LogoutSuccessHandler  {

	/**
	 * Method onLogoutSuccess.
	 * 
	 * Method that override the default method with the custom configuration.
	 * 
	 */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String lang = request.getParameter("lang");
        System.out.println(lang);
        if (lang != null && !lang.isEmpty()) {
            response.sendRedirect("/?lang=" + lang);
        } else {
            response.sendRedirect("/");
        }
    }


}
