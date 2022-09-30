package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import spark.Spark;

import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String... args){
        port(getPort());
        //port(4567);
        Spark.staticFiles.header("Access-Control-Allow-Origin", "*");
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.type("application/json");
        });


        //Endpoints
            path("/api/messages", () -> {
                post("", (req, res) -> {
                    res.type("application/json");
                    System.out.println("aa");

                    MongoDatabase dbmongo = openConnection();


                    MongoCollection table = dbmongo.getCollection("messages");
                    Document document = new Document().append("about", "Open-Source database");
                    table.insertOne(document);

                    return "ok";
                });
            });

    }

    public static MongoDatabase openConnection(){
        ConnectionString connectionString = new ConnectionString("mongodb://pacho:pacho@ac-dwefend-shard-00-00.mblvif0.mongodb.net:27017,ac-dwefend-shard-00-01.mblvif0.mongodb.net:27017,ac-dwefend-shard-00-02.mblvif0.mongodb.net:27017/?ssl=true&replicaSet=atlas-wk6jcp-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        return database;
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}