<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Evento</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container my-5">
    <h2 class="mb-4">Registrar Evento</h2>

    <!-- Mensaje de error general -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="guardar" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label for="titulo" class="form-label">Título:</label>
            <input type="text" class="form-control" id="titulo" name="titulo" value="${evento.titulo}" required>
        </div>

        <div class="mb-3">
            <label for="fecha" class="form-label">Fecha:</label>
            <input type="date" class="form-control" id="fecha" name="fecha" value="${evento.fecha}" required>
        </div>

        <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción:</label>
            <textarea class="form-control" id="descripcion" name="descripcion" rows="4">${evento.descripcion}</textarea>
        </div>

        <div class="mb-3">
            <label for="responsable" class="form-label">Responsable:</label>
            <input type="text" class="form-control" id="responsable" name="responsable" value="${evento.responsable}">
        </div>

        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>

    <!-- Validaciones visuales de longitud -->
    <c:if test="${fn:length(evento.titulo) > 50}">
        <div class="alert alert-warning mt-3">El título es demasiado largo (máx. 50 caracteres).</div>
    </c:if>

    <c:if test="${fn:length(evento.descripcion) > 200}">
        <div class="alert alert-warning mt-3">La descripción es demasiado larga (máx. 200 caracteres).</div>
    </c:if>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
