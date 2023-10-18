package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongoDB {
    private static final String MONGO_URI = "mongodb://mongo:5PkKtudNlmIdsXA3PKE0@containers-us-west-128.railway.app:7809";
    private static final String DATABASE_NAME = "railway";

    private static final MongoClient mongoClient = MongoClients.create(MONGO_URI);
    private static final MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

    public static MongoDatabase getDatabase() {
        return database;
    }
}
