package com.db.ys.servlet;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
@WebServlet(value = "/ys")
public class ys extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String str=request.getParameter("pid");
    }
}
