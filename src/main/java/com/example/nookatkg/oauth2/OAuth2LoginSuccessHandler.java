package com.example.nookatkg.oauth2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println("OAuth2 username: " + oAuth2User.getName());
        System.out.println("OAuth2 email: " + oAuth2User.getEmail());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
