/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OCMS.Controller;

import OCMS.EJB.UserEJB;
import OCMS.Entity.Users;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SabinRegmi
 */
public class LoginServlet extends HttpServlet {

    @EJB
    private UserEJB userEJB;
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String baseUrl = servletRequest.getServletContext().getContextPath();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        Users user = new Users();

        try {
            user = userEJB.loginUser(userName, password);
            HttpSession session = request.getSession(); //Creating a session
            if (user != null) {
                session.setAttribute("user", user);
                if (user.getRole().equals("Admin")) {
                    baseUrl = baseUrl + "/admin/dashboard.xhtml";

                } else if (user.getRole().equals("Author") && user.isIsApproved()) {
                    baseUrl = baseUrl + "/author/dashboard.xhtml";

                } else if (user.getRole().equals("Organizer") && user.isIsApproved()) {
                    baseUrl = baseUrl + "/organizer/dashboard.xhtml";

                } else if (user.getRole().equals("Participant")) {
                    baseUrl = baseUrl + "/participant/dashboard.xhtml";

                } else {
                    request.setAttribute("errMessage", "Roles is not defined.");
                    baseUrl = baseUrl + "/login.xhtml";
                }
                //request.getRequestDispatcher(baseUrl).forward(request, response);
                response.sendRedirect(baseUrl);
            } else {
                request.setAttribute("errMessage", "Username or password incorrect.");
                response.sendRedirect(baseUrl + "/login.xhtml");

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
