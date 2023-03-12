package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "indexServlet", value="/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DbConnection dbConnection = new DbConnection();

        HttpSession currentSession = request.getSession();
        currentSession.setMaxInactiveInterval(7200);
        currentSession.setAttribute("dbConnection", dbConnection.getConnection());

        response.sendRedirect("page.jsp");

    }
}
