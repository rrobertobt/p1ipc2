package com.robertob.p1ipc2.servlet;

import com.robertob.p1ipc2.database.DbProduct;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private DbProduct dbProduct;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("dbConnection");
        dbProduct = new DbProduct(connection);

        request.setAttribute("products", dbProduct.list());
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}
