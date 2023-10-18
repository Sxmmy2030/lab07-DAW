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
            <h2 class="text-center mb-4">Datos del Producto</h2>
            
            <form action="ControladorProducto" method="post" class="bg-secondary p-4 rounded">
                <input type="hidden" name="action" value="actualizar">
                <input type="hidden" name="codigo" value="${producto.codigo}">

                <div class="mb-3">
                    <label for="codigo" class="form-label">Código</label>
                    <input type="text" name="codigo" class="form-control" id="codigo" value="${producto.codigo}" disabled>
                </div>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" id="nombre" value="${producto.nombre}" disabled>
                </div>

                <div class="mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="text" name="precio" class="form-control" id="precio" value="${producto.precio}" disabled>
                </div>

                <div class="mb-3">
                    <label for="stock" class="form-label">Stock</label>
                    <input type="text" name="stock" class="form-control" id="stock" value="${producto.stock}" disabled>
                </div>
            </form>
            
            <div class="text-center mt-3">
                <a href="ControladorProducto" class="btn btn-light">Regresar a Inicio</a>
            </div>
            
        </div>
        
    </div>
</div>
</body>
</html>
