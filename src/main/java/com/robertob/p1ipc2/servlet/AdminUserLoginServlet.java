package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbAdminUser;
import com.robertob.p1ipc2.database.DbStoreUser;
import com.robertob.p1ipc2.model.AdminUser;
import com.robertob.p1ipc2.model.StoreUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/AdminLoginServlet")
public class AdminUserLoginServlet extends HttpServlet {
    private AdminUser adminUser;
    private DbAdminUser dbAdminUser;

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
        AdminUser adminUser = (AdminUser) session.getAttribute("user");

        if (adminUser != null) {
            response.sendRedirect("adminUserLogin.jsp");
            return;
        }

        dbAdminUser = new DbAdminUser(dbConnection);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (validateUser(username, password)){
            session.setAttribute("currentUser", this.adminUser);
            response.sendRedirect("adminMain.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas o usuario inactivo");
            request.getRequestDispatcher("adminUserLogin.jsp").forward(request, response);
        }
    }

    public boolean validateUser(String username, String password){
        var dbUser = dbAdminUser.findByUserPassword(username, password);
        if(dbUser.isEmpty()) return false;

        adminUser = dbUser.get();
        return true;
    }
}
