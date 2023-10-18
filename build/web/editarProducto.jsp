<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shop Store</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-white">

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h2 class="text-center mb-4">Editar Producto</h2>
            
            <form action="ControladorProducto" method="post" class="bg-secondary p-4 rounded">
                <input type="hidden" name="action" value="actualizar">
                <input type="hidden" name="codigo" value="${producto.codigo}">

                <div class="mb-3">
                    <label for="codigo" class="form-label">Código</label>
                    <input type="text" name="codigo" class="form-control" id="codigo" value="${producto.codigo}" required>
                </div>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" id="nombre" value="${producto.nombre}" required>
                </div>

                <div class="mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="text" name="precio" class="form-control" id="precio" value="${producto.precio}" required>
                </div>

                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="text" name="stock" class="form-control" id="stock" value="${producto.stock}" required>
                </div>

                <div class="d-grid gap-2">
                    <input type="submit" value="Guardar Cambios" class="btn btn-primary me-2" />
                    <input type="reset" value="Resetear Cambios" class="btn btn-outline-warning" />
                </div>
            </form>
            
            <div class="text-center mt-3">
                <a href="ControladorProducto?action=listar" class="btn btn-light">Regresar a Inicio</a>
            </div>
            
        </div>
        
    </div>
</div>
                
<script>
    document.getElementById("registroForm").addEventListener("submit", function(event) {
        var nombre = document.getElementById("nombre").value;
        var precio = document.getElementById("precio").value;
        var stock = document.getElementById("stock").value;

        if (!nombre || nombre.length < 3) {
            alert("El nombre debe tener al menos 3 caracteres.");
            event.preventDefault();
        }

        if (!precio || isNaN(precio) || precio <= 0) {
            alert("Ingrese un precio válido.");
            event.preventDefault();
        }

        if (!stock || isNaN(stock) || stock < 0) {
            alert("Ingrese un stock válido.");
            event.preventDefault();
        }
    });
</script>
                
</body>
</html>