<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Eventos</title>
    <style>
        h3 { color: #2a7ae2; }
        ul { list-style-type: square; padding-left: 20px; }
        li { margin-bottom: 6px; }
        .empty { color: gray; font-style: italic; }
        .descripcion-larga { color: red; }
        .descripcion-corta { color: green; }
    </style>
</head>
<body>
<h2>Eventos Agrupados por Fecha</h2>

<c:if test="${empty eventosAgrupados}">
    <p class="empty">No hay eventos registrados.</p>
</c:if>

<c:forEach var="entrada" items="${eventosAgrupados}">
    <h3>
        <!-- Formateo de fecha: yyyy-MM-dd → dd/MM/yyyy -->
        <fmt:formatDate value="${entrada.key}" pattern="dd/MM/yyyy"/>
    </h3>
    <ul>
        <c:forEach var="evento" items="${entrada.value}">
            <li>
                <strong><c:out value="${evento.titulo}"/></strong> -
                <c:out value="${evento.descripcion}"/>
                (<c:out value="${evento.responsable}"/>)
                
                <!-- c:choose con fn:length -->
                <c:choose>
                    <c:when test="${fn:length(evento.descripcion) > 50}">
                        <span class="descripcion-larga">(Descripción larga)</span>
                    </c:when>
                    <c:otherwise>
                        <span class="descripcion-corta">(Descripción corta)</span>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</c:forEach>

</body>
</html>
