package dao;

import models.Producto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOMongoDB implements ProductoDAO {
    private static final String DATABASE_NAME = "railway";
    private static final String COLLECTION_NAME = "productos";

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public ProductoDAOMongoDB() {
        // Establece una conexión con la instancia local de MongoDB
        this.mongoClient = MongoClients.create();
        this.database = mongoClient.getDatabase(DATABASE_NAME);
        this.collection = database.getCollection(COLLECTION_NAME);
    }

    @Override
    public void insertar(Producto producto) {
        Document doc = new Document("nombre", producto.getNombre())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock());

        collection.insertOne(doc);
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();

        for (Document doc : collection.find(Filters.regex("nombre", nombre))) {
            productos.add(documentToProducto(doc));
        }

        return productos;
    }

    @Override
    public List<Producto> listarTodos(int pagina, int cantidadPorPagina) {
        List<Producto> productos = new ArrayList<>();

        collection.find().skip((pagina - 1) * cantidadPorPagina).limit(cantidadPorPagina)
                .forEach(document -> productos.add(documentToProducto(document)));

        return productos;
    }

    @Override
    public int obtenerTotalProductos() {
        return (int) collection.countDocuments();
    }

    @Override
    public void actualizar(Producto producto) {
        Document updateDoc = new Document("nombre", producto.getNombre())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock());

        collection.updateOne(Filters.eq("codigo", producto.getCodigo()), new Document("$set", updateDoc));
    }

    @Override
    public void eliminar(int codigo) {
        collection.deleteOne(Filters.eq("codigo", codigo));
    }

    @Override
    public Producto ver(int codigo) {
        Document doc = collection.find(Filters.eq("codigo", codigo)).first();
        if (doc != null) {
            return documentToProducto(doc);
        }
        return null;
    }

    @Override
    public String getDatabaseType() {
        return "MongoDB";
    }

    private Producto documentToProducto(Document doc) {
        Producto producto = new Producto();
        producto.setCodigo(doc.getInteger("codigo"));
        producto.setNombre(doc.getString("nombre"));
        producto.setPrecio(doc.getDouble("precio"));
        producto.setStock(doc.getInteger("stock"));
        return producto;
    }
}
