package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbConnection;
import com.robertob.p1ipc2.database.DbHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "indexServlet", value="/index")
public class IndexServlet extends HttpServlet {

    DbHelper dbHelper = new DbHelper();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DbConnection dbConnection = new DbConnection();

        HttpSession currentSession = request.getSession();
        currentSession.setMaxInactiveInterval(7200);
        currentSession.setAttribute("dbConnection", dbConnection.getConnection());
        dbHelper.setConnection((Connection) currentSession.getAttribute("dbConnection"));
        currentSession.setAttribute("dbHasRecords", dbHelper.checkForRecords());

        if (!dbHelper.checkForRecords()) {
            response.sendRedirect("load_seeds.jsp");
        } else {
            response.sendRedirect("storeUserLogin.jsp");
        }
    }
}
