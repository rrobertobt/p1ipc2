<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Iniciar sesion - Tiendas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/login-nav/AdminLoginNav.jsp"/>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title text-center">Bienvenido: Iniciar sesión</h3>
                    <h6 class="text-center">Usuario administrador</h6>
                    <form id="form-login" action="${pageContext.request.contextPath}/AdminLoginServlet" method="POST">
                        <div class="mb-3">
                            <label for="username" class="form-label">Nombre de usuario</label>
                            <input type="text" class="form-control" id="username" name="username">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Contrasena</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <c:if test="${!empty(error)}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>Hubo un error al iniciar sesion:</strong> ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <div class="text-center d-grid gap-2">
                            <button type="submit" class="btn btn-danger"> <strong>INICIAR SESIÓN</strong> </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">No eres usuario administrador?</h5>
                    <h6 style="font-weight: normal">Iniciar sesión como:</h6>

                    <div class="container text-center">
                        <div class="row justify-content-start">
                            <div class="col">
                                <a href="storeUserLogin.jsp" class="btn btn-light"> <strong>TIENDAS</strong> </a>
                            </div>
                            <div class="col">
                                <a href="warehouseUserLogin.jsp" class="btn btn-light"> <strong>BODEGAS</strong> </a>
                            </div>
                            <div class="col">
                                <a href="supervisorUserLogin.jsp" class="btn btn-light"> <strong>SUPERVISOR</strong> </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!--JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
</body>
</html>
