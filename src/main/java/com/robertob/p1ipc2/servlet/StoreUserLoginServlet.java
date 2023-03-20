package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbStoreUser;
import com.robertob.p1ipc2.model.Store;
import com.robertob.p1ipc2.model.StoreUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/StoreUserLoginServlet")
public class StoreUserLoginServlet extends HttpServlet {
    private StoreUser storeUser;
    private DbStoreUser dbStoreUser;

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
        StoreUser storeUser = (StoreUser) session.getAttribute("user");

        if (storeUser != null) {
            response.sendRedirect("storeMain.jsp");
            return;
        }

        dbStoreUser = new DbStoreUser(dbConnection);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (validateUser(username, password)){
            session.setAttribute("currentUser", storeUser);
            response.sendRedirect("storeMain.jsp");
        } else {
            request.setAttribute("error", "Credenciales incorrectas o usuario inactivo");
            request.getRequestDispatcher("storeUserLogin.jsp").forward(request, response);
        }
    }

    public boolean validateUser(String username, String password){
        var dbUser = dbStoreUser.findByUserPassword(username, password);
        if(dbUser.isEmpty()) return false;

        storeUser = dbUser.get();
        return true;
    }
}
