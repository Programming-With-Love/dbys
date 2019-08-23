package com.db.ys.servlet;

import com.db.ys.service.yssj;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/syhq")
public class syhq extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doGet(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        yssj sj=new yssj();
        sj.getsysj();
        //RequestDispatcher rd = request.getRequestDispatcher("/sy.jsp");
       // rd.forward(request, response);
        response.getWriter().write("123");
    }
}
