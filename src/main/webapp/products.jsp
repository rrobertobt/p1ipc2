<%--
  Created by IntelliJ IDEA.
  User: robertob
  Date: 11/03/23
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
    <h1>Products</h1>
    <table>
        <thead>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Cost</th>
            <th>Price</th>
            <th>Stock</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}" varStatus="status">
            <tr>
                <th>${product.code}</th>
                <th>${product.name}</th>
                <th>${product.cost}</th>
                <th>${product.price}</th>
                <th>${product.stock}</th>
            </tr>
        </c:forEach>
        </tbody>
    </table>


</body>
</html>
