<!DOCTYPE html>
<html>
<head>
    <title>Shop Store</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" href="icon/59307bddccae01b24826684724771a41.ico" type="image/x-icon">
</head>
<body class="bg-dark text-white">

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h2 class="text-center mb-4">Insertar Producto</h2>
            
            <form action="ControladorProducto" method="post" class="bg-secondary p-4 rounded">
                <input type="hidden" name="action" value="insertar" />

                <div class="mb-3">
                    <label for="codigo" class="form-label">Código</label>
                    <input type="number" name="codigo" class="form-control" id="codigo" required>
                </div>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" id="nombre" required>
                </div>

                <div class="mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="double" name="precio" class="form-control" id="precio" required>
                </div>

                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="number" name="stock" class="form-control" id="stock" required>
                </div>

                <div class="d-grid gap-2">
                    <input type="submit" value="Insertar" class="btn btn-primary me-2" />
                    <input type="reset" value="Limpiar" class="btn btn-outline-light" />
                </div>
            </form>
            
            <div class="text-center mt-3">
                <a href="ControladorProducto?action=listar" class="btn btn-light">Regresar a Inicio</a>
            </div>
            
        </div>
        
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
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