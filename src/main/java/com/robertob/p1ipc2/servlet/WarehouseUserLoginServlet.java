package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbSupervisorUser;
import com.robertob.p1ipc2.database.DbWarehouseUser;
import com.robertob.p1ipc2.model.SupervisorUser;
import com.robertob.p1ipc2.model.WarehouseUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/WarehouseUserLogin")
public class WarehouseUserLoginServlet extends HttpServlet {
    private WarehouseUser warehouseUser;
    private DbWarehouseUser dbWarehouseUser;

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
        WarehouseUser warehouseUser = (WarehouseUser) session.getAttribute("user");

        if (warehouseUser != null) {
            response.sendRedirect("warehouseMain.jsp");
            return;
        }

        dbWarehouseUser = new DbWarehouseUser(dbConnection);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (validateUser(username, password)){
            session.setAttribute("currentUser", this.warehouseUser);
            response.sendRedirect("warehouseMain.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas o usuario inactivo");
            request.getRequestDispatcher("warehouseUserLogin.jsp").forward(request, response);
        }
    }

    public boolean validateUser(String username, String password){
        var dbUser = dbWarehouseUser.findByUserPassword(username, password);
        if(dbUser.isEmpty()) return false;

        warehouseUser = dbUser.get();
        return true;
    }
}
