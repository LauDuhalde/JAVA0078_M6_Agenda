<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eventos</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Eventos Agrupados por Fecha</h2>
        <a href="${pageContext.request.contextPath}/agenda/nuevo" class="btn btn-primary">Añadir Nuevo Evento</a>
    </div>

    <c:if test="${empty eventosAgrupados}">
        <div class="alert alert-secondary" role="alert">
            No hay eventos registrados.
        </div>
    </c:if>

    <c:forEach var="entrada" items="${eventosAgrupados}">
        <div class="card mb-3">
            <div class="card-header bg-primary text-white fw-bold">
                ${entrada.key}
            </div>
            <ul class="list-group list-group-flush">
                <c:forEach var="evento" items="${entrada.value}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <strong><c:out value="${evento.titulo}"/></strong> - 
                            <c:out value="${evento.descripcion}"/> 
                            (<c:out value="${evento.responsable}"/>)
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${fn:length(evento.descripcion) > 50}">
                                    <span class="badge bg-danger">Descripción larga</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-success">Descripción corta</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:forEach>
</div>

<!-- Bootstrap JS Bundle (opcional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
