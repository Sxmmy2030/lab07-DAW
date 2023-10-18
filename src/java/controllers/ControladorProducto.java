package controllers;

import dao.DAOFactory;
import dao.ProductoDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Producto;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControladorProducto", urlPatterns = {"/ControladorProducto"})
public class ControladorProducto extends HttpServlet {

    private ProductoDAO productoDAO;  // Nota: No se inicializa aquí, lo haremos en el método de servicio según el tipo de DB.
    
    String mensaje;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException { 
        response.setContentType("text/html;charset=UTF-8");
        
        String dbType = request.getParameter("db");
        if (dbType != null && !dbType.trim().isEmpty()) {
            request.getSession().setAttribute("db", dbType);
        } else {
            dbType = (String) request.getSession().getAttribute("db");
            if (dbType == null || dbType.trim().isEmpty()) {
                throw new ServletException("Tipo de base de datos no especificado.");
            }
        }
        
        productoDAO = DAOFactory.getProductoDAO(dbType);  // Aquí inicializamos el DAO según el tipo de DB proporcionado.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);

            mensaje = (String) request.getSession().getAttribute("mensaje");
    request.getSession().removeAttribute("mensaje");
    request.setAttribute("mensaje", mensaje);
    
    String action = request.getParameter("action");
    
    switch (action) {
        case "ver":
            int codigoVer = Integer.parseInt(request.getParameter("codigo"));
            Producto productoVer = productoDAO.ver(codigoVer);
            request.setAttribute("producto", productoVer);
            request.getRequestDispatcher("verProducto.jsp").forward(request, response);
            break;

        case "editar":
            int codigoEdit = Integer.parseInt(request.getParameter("codigo"));
            Producto productoEdit = productoDAO.ver(codigoEdit);
            request.setAttribute("producto", productoEdit);
            request.getRequestDispatcher("editarProducto.jsp").forward(request, response);
            break;

        case "eliminar":
            mensaje = "Producto eliminado con éxito!";
            int codigoDel = Integer.parseInt(request.getParameter("codigo"));
            productoDAO.eliminar(codigoDel);
            request.getSession().setAttribute("mensaje", mensaje);
            response.sendRedirect("ControladorProducto?action=listar");
            break;

        case "buscarP":
            String nombreBuscar = request.getParameter("nombreBuscar");
            List<Producto> productosBuscar = productoDAO.buscarProductosPorNombre(nombreBuscar);
            Gson gson = new Gson();
            String productosJson = gson.toJson(productosBuscar);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(productosJson);
            break;

        case "mostrarP":
            String nombreMostrar = request.getParameter("nombreBuscar");
            List<Producto> productosMostrar = productoDAO.buscarProductosPorNombre(nombreMostrar);
            request.setAttribute("productos", productosMostrar);
            request.getRequestDispatcher("mostrarProductos.jsp").forward(request, response);
            break;

        case "listar":
            String strPagina = request.getParameter("pagina");
            int pagina = (strPagina != null && !strPagina.isEmpty()) ? Integer.parseInt(strPagina) : 1;
            int totalProductos = productoDAO.obtenerTotalProductos();
            int cantidadPorPagina = 10;
            int totalPages = (int) Math.ceil((double) totalProductos / cantidadPorPagina);
            pagina = Math.max(1, Math.min(pagina, totalPages));
            List<Producto> productos = productoDAO.listarTodos(pagina, cantidadPorPagina);
            request.setAttribute("productos", productos);
            request.setAttribute("pagina", pagina);
            request.getRequestDispatcher("productos.jsp").forward(request, response);
    }
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            processRequest(request, response);
            
             if (!validarDatos(request, response)) {
        return;
    }
    
    String action = request.getParameter("action");

    switch (action) {
        case "insertar":
            manejarInsercion(request, response);
            break;

        case "buscar":
            manejarBusqueda(request, response);
            break;

        case "actualizar":
            manejarActualizacion(request, response);
            break;

        default:
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<body>");
            out.println("<h2>Acción no reconocida</h2>");
            out.println("<p>Lo siento, la acción que intentaste realizar no es válida.</p>");
            out.println("</body>");
            out.println("</html>");
    }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private boolean validarDatos(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    String nombreP = request.getParameter("nombre");
    String precioP = request.getParameter("precio");
    String stockP = request.getParameter("stock");

    if (nombreP == null || nombreP.length() < 3) {
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return false;
    }

    try {
        double precio = Double.parseDouble(precioP);
        if (precio <= 0) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return false;
        }
    } catch (NumberFormatException e) {
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return false;
    }

    try {
        int stock = Integer.parseInt(stockP);
        if (stock < 0) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return false;
        }
    } catch (NumberFormatException e) {
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
        return false;
    }

    return true;
}

private void manejarInsercion(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    String mensaje = "Producto agregado con éxito!";
    String codigo = request.getParameter("codigo");
    String nombre = request.getParameter("nombre");
    double precio = Double.parseDouble(request.getParameter("precio"));
    int stock = Integer.parseInt(request.getParameter("stock"));
    
    Producto nuevoProducto = new Producto();
    nuevoProducto.setCodigo(Integer.parseInt(codigo));
    nuevoProducto.setNombre(nombre);
    nuevoProducto.setPrecio(precio);
    nuevoProducto.setStock(stock);
    
    productoDAO.insertar(nuevoProducto);
    request.getSession().setAttribute("mensaje", mensaje);
    response.sendRedirect("ControladorProducto?action=listar");
}

private void manejarBusqueda(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    String nombreBuscar = request.getParameter("nombreBuscar");
    List<Producto> productos = productoDAO.buscarProductosPorNombre(nombreBuscar);

    StringBuilder nombres = new StringBuilder();
    for (Producto producto : productos) {
        nombres.append(producto.getNombre()).append(",");
    }

    if (nombres.length() > 0) {
        nombres.setLength(nombres.length() - 1);
    }

    response.getWriter().write(nombres.toString());
}

private void manejarActualizacion(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    String mensaje = "Producto actualizado con éxito!";
    String codigo = request.getParameter("codigo");
    String nombre = request.getParameter("nombre");
    double precio = Double.parseDouble(request.getParameter("precio"));
    int stock = Integer.parseInt(request.getParameter("stock"));
    
    Producto Product = new Producto();
    Product.setCodigo(Integer.parseInt(codigo));
    Product.setNombre(nombre);
    Product.setPrecio(precio);
    Product.setStock(stock);
    
    productoDAO.actualizar(Product);
    request.getSession().setAttribute("mensaje", mensaje);
    response.sendRedirect("ControladorProducto?action=listar");
}
    
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
