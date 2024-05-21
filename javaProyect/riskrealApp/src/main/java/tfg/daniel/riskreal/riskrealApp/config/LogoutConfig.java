package tfg.daniel.riskreal.riskrealApp.config;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogoutConfig implements LogoutSuccessHandler  {

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
