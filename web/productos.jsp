<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shop Store</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="icon" href="icon/59307bddccae01b24826684724771a41.ico" type="image/x-icon">
</head>

<style>

#resultadosBusqueda {
    background-color: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(10px);
    max-height: 125px;
    overflow-y: auto;
    text-align: center;
    border: 1px solid rgba(255, 255, 255, 0.2);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    display: flex;          /* Nuevo */
    flex-direction: column; /* Nuevo */
    justify-content: center;/* Nuevo */
}

#resultadosBusqueda a, #resultadosBusqueda p {
    display: block;
    margin: 5px 0;
    color: white;
    text-decoration: none;
}


#resultadosBusqueda a:hover {
    text-decoration: underline;
}

/* Diseño del scrollbar */
#resultadosBusqueda::-webkit-scrollbar {
    width: 8px; /* Ancho del scrollbar */
}

#resultadosBusqueda::-webkit-scrollbar-thumb {
    background-color: rgba(255, 255, 255, 0.5); /* Color del "pulgar" del scrollbar */
    border-radius: 4px; /* Bordes redondeados para el pulgar */
}

#resultadosBusqueda::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.2); /* Color de fondo del área del scrollbar */
}

#resultadosBusqueda::-webkit-scrollbar-thumb:hover {
    background-color: rgba(255, 255, 255, 0.7); /* Color del pulgar al hacer hover */
}

.alert {
    padding: 10px 15px;
    margin: 10px;
    background-color: rgba(173, 216, 230, 0.5);
    backdrop-filter: blur(10px); /* Efecto acrílico */
    opacity: 1;
    position: fixed; /* Esto asegura que el mensaje esté en la misma posición sin importar el scroll de la página */
    bottom: 10px; /* Distancia desde el borde inferior */
    right: 10px; /* Distancia desde el borde derecho */
    z-index: 1000;
    transition: opacity 0.3s ease, visibility 0.3s ease; /* Efecto de transición para el desvanecimiento */
}

.alert.fade-out {
    opacity: 0;
}

body::-webkit-scrollbar {
    width: 8px;
}

body::-webkit-scrollbar-thumb {
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 4px;
}

body::-webkit-scrollbar-track {
    background-color: rgba(0, 0, 0, 0.2);
}

body::-webkit-scrollbar-thumb:hover {
    background-color: rgba(255, 255, 255, 0.5);
}

@media (max-width: 768px) {
    table th, table td {
        text-align: center;
        padding: 15px 5px;
    }
    table td a.btn {
        margin-top: 5px;
    }
}

#backButton {
    position: absolute;
    top: 10px;
    left: 10px;
    z-index: 2000;
}

/* Estilo que oculta el texto en dispositivos con un ancho máximo de 768px (por ejemplo, teléfonos móviles) */
@media (max-width: 768px) {
    .back-text {
        display: none;
    }
}

</style>

<body class="bg-dark text-white">

<c:if test="${not empty mensaje}">
    <div class="alert ${alertClass}" id="alertMessage">
        ${mensaje}
    </div>
</c:if>
    
<a href="index.html" class="btn btn-light" id="backButton"><i class="bi bi-arrow-left-circle-fill"></i> <span class="back-text">Volver a la página principal</span></a>

<div class="container mt-3">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <h2 class="text-center mb-4">Productos</h2>
            
            <table class="table table-dark table-striped text-center">
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="producto" items="${productos}">
                        <tr>
                            <td>${producto.codigo}</td>
                            <td>${producto.nombre}</td>
                            <td>${producto.precio}</td>
                            <td>${producto.stock}</td>
                            <td>
                                <a href="ControladorProducto?action=editar&codigo=${producto.codigo}" class="btn btn-primary me-1"><i class="bi bi-pencil-fill"></i></a>
                                <a href="ControladorProducto?action=eliminar&codigo=${producto.codigo}" onclick="return confirm('¿Estás seguro de que quieres eliminar este producto?');" class="btn btn-danger"><i class="bi bi-trash2-fill"></i></a>
                                <a href="ControladorProducto?action=ver&codigo=${producto.codigo}" class="btn btn-success"><i class="bi bi-eye-fill"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <div class="mb-3">
                <a href="productoRegistro.jsp" class="btn btn-success">Agregar</a>
            </div>
            
            <nav>
                <ul class="pagination">
                  <li class="page-item">
                    <a class="page-link" href="ControladorProducto?action=listar&pagina=${pagina - 1}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <li class="page-item"><a class="page-link" href="#">${pagina}</a></li>
                  <li class="page-item">
                    <a class="page-link" href="ControladorProducto?action=listar&pagina=${pagina + 1}" aria-label="Next">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
            </nav>
            
            <div class="d-flex">
                <input type="text" id="nombreBuscar" class="form-control me-2" placeholder="Buscar por nombre...">
                <a href="#" id="btnBuscar" class="btn btn-primary me-2">Buscar</a>
                <input type="reset" id="limpiarBtn" value="Limpiar" class="btn btn-light" />
            </div>

            <div id="resultadosBusqueda" class="mt-3 rounded"></div>
            
        </div>
    </div>
</div>
</body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
     $("#nombreBuscar").on("input", function() {
         var query = $(this).val();
         if(query.length > 0) {
             $.ajax({
                 url: 'ControladorProducto',
                 method: 'GET',
                 dataType: 'json',
                 data: {
                     nombreBuscar: query,
                     action: 'buscarP'
                 },
                 success: function(data) {
                     if(data.length === 0) {
                         $('#resultadosBusqueda').html('<p>No se encontraron resultados.</p>');
                         $('#nombreBuscar').attr('data-empty', 'true'); // Establecer data-empty cuando no hay resultados
                     } else {
                         var contenido = '';
                         data.forEach(function(producto) {
                             contenido += '<a href="ControladorProducto?action=ver&codigo=' + producto.codigo + '">' + producto.nombre + '</a>'; 
                         });
                         $('#resultadosBusqueda').html(contenido);
                         $('#resultadosBusqueda').css('height', 'auto');
                         $('#nombreBuscar').attr('data-empty', 'false'); // Establecer data-empty cuando hay resultados
                     }
                     },
                     error: function(error) {
                         console.error("Hubo un error en la búsqueda:", error);
                     }
                 });
             } else {
                 $('#resultadosBusqueda').empty();
             }
         });
     });

    document.getElementById('limpiarBtn').addEventListener('click', function(event) {
        // Evita el comportamiento predeterminado del botón
        event.preventDefault();

        // Obtiene una referencia al input de búsqueda
        var inputBuscar = document.getElementById('nombreBuscar');

        // Limpia el valor del input de búsqueda
        inputBuscar.value = '';

        // Limpia el contenido de los resultados de búsqueda
        document.getElementById('resultadosBusqueda').innerHTML = '';

        // Establece el foco en el input de búsqueda
        inputBuscar.focus();
    });

    document.getElementById('btnBuscar').addEventListener('click', function(event) {
        event.preventDefault();

        var nombreBuscar = document.getElementById('nombreBuscar').value.trim();

        if(nombreBuscar === "") {
            alert("Por favor, ingrese un nombre para buscar.");
            return;
        }

        // Verificar si no hay resultados
        if(document.getElementById('nombreBuscar').getAttribute('data-empty') === 'true') {
            alert("No se encontraron resultados para su búsqueda.");
            return;
        }

        var url = "ControladorProducto?action=mostrarP&nombreBuscar=" + nombreBuscar;
        window.location.href = url;
    });

   document.addEventListener("DOMContentLoaded", function() {
    let alertElem = document.getElementById("alertMessage");
    if (alertElem) {
            setTimeout(function() {
                alertElem.classList.add('fade-out');
                setTimeout(function() {
                    alertElem.style.display = 'none';
                }, 300);
            }, 2000);
        }
    });

</script>

</body>
</html>