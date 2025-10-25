<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar Evento</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        label { display: block; margin-top: 8px; font-weight: bold; }
        input, textarea { width: 300px; padding: 5px; margin-top: 4px; }
        button { margin-top: 10px; padding: 6px 12px; }
        .error { color: red; margin-top: 10px; }
    </style>
</head>
<body>
<h2>Registrar Evento</h2>

<!-- Mensaje de error si existe -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<form action="guardar" method="post">
    <label>T�tulo:</label>
    <input type="text" name="titulo" value="${evento.titulo}" required/>

    <label>Fecha:</label>
    <input type="date" name="fecha" value="${evento.fecha}" required/>

    <label>Descripci�n:</label>
    <textarea name="descripcion">${evento.descripcion}</textarea>

    <label>Responsable:</label>
    <input type="text" name="responsable" value="${evento.responsable}"/>

    <button type="submit">Guardar</button>
</form>

<!-- Validaciones adicionales (ejemplo visual) -->
<c:if test="${fn:length(evento.titulo) > 50}">
    <p class="error">El t�tulo es demasiado largo (m�x. 50 caracteres).</p>
</c:if>

<c:if test="${fn:length(evento.descripcion) > 200}">
    <p class="error">La descripci�n es demasiado larga (m�x. 200 caracteres).</p>
</c:if>

</body>
</html>
