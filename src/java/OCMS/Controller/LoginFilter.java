/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.Entity.Users;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SabinRegmi
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter.super.init(filterConfig); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String url = servletRequest.getRequestURI();
        HttpSession session = servletRequest.getSession(false);
        /*
        A. If request is for to access authorized page and there is no session, redirect request to login page
        B. If request is for register or login and there is session, redirect to respective dashboard.
        C. If request is for logout and there is session, remove the session, then redirect to login page.
         */
        if ((session == null || session.getAttribute("user") == null) &&(url.toLowerCase().contains("/login.xhtml") || url.toLowerCase().contains("/register.xhtml"))) {
            chain.doFilter(request, response);
        } else if (session == null || session.getAttribute("user") == null) {
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/login.xhtml"); // No logged-in user found, so redirect to login page.
        } else if (session != null && session.getAttribute("user") != null) {
            Users user = (Users) session.getAttribute("user");
            if (user.getRole().equals("Author") && (url.toLowerCase().contains("/author/"))) {
                chain.doFilter(request, response); // Logged-in user found, so just continue request.
            } else if (user.getRole().equals("Participant") && url.toLowerCase().contains("/participant/")) {
                chain.doFilter(request, response); // Logged-in user found, so just continue request.
            } else if (user.getRole().equals("Admin") && url.toLowerCase().contains("/admin/")) {
                chain.doFilter(request, response); // Logged-in user found, so just continue request.
            } else if (user.getRole().equals("Organizer") && url.toLowerCase().contains("/organizer/")) {
                chain.doFilter(request, response); // Logged-in user found, so just continue request.
            } else if (user.getRole().equals("Author") && (url.toLowerCase().contains("/login.xhtml") || url.toLowerCase().contains("/register.xhtml"))) {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/author/dashboard.xhtml");
            } else if (user.getRole().equals("Participant") && (url.toLowerCase().contains("/login.xhtml") || url.toLowerCase().contains("/register.xhtml"))) {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/participant/dashboard.xhtml");
            } else if (user.getRole().equals("Admin") && (url.toLowerCase().contains("/login.xhtml") || url.toLowerCase().contains("/register.xhtml"))) {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/admin/dashboard.xhtml");
            } else if (user.getRole().equals("Organizer") && (url.toLowerCase().contains("/login.xhtml") || url.toLowerCase().contains("/register.xhtml"))) {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/organizer/dashboard.xhtml");
            } else {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/login.xhtml"); // No logged-in user found, so redirect to login page.
            }
        } else {
            chain.doFilter(request, response); // Logged-in user found, so just continue request.
        }
    }

    @Override
    public void destroy() {
    }

}
