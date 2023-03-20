package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbStoreUser;
import com.robertob.p1ipc2.database.DbSupervisorUser;
import com.robertob.p1ipc2.model.StoreUser;
import com.robertob.p1ipc2.model.SupervisorUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/SupervisorUserLogin")
public class SupervisorUserLoginServlet extends HttpServlet {

    private SupervisorUser supervisorUser;
    private DbSupervisorUser dbSupervisorUser;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Connection dbConnection = (Connection) session.getAttribute("dbConnection");
        SupervisorUser supervisorUser = (SupervisorUser) session.getAttribute("user");

        if (supervisorUser != null) {
            response.sendRedirect("supervisorMain.jsp");
            return;
        }

        dbSupervisorUser = new DbSupervisorUser(dbConnection);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (validateUser(username, password)){
            session.setAttribute("currentUser", supervisorUser);
            response.sendRedirect("supervisorMain.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas o usuario inactivo");
            request.getRequestDispatcher("supervisorUserLogin.jsp").forward(request, response);
        }
    }

    public boolean validateUser(String username, String password){
        var dbUser = dbSupervisorUser.findByUserPassword(username, password);
        if(dbUser.isEmpty()) return false;

        supervisorUser = dbUser.get();
        return true;
    }
}
