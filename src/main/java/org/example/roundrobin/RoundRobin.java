package org.example.roundrobin;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import spark.Spark;

import static spark.Spark.*;

public class RoundRobin {
    private static final Integer[] servers = {35001, 35002, 35003};
    private static Integer serverNumber = 0;
    private static final String baseURL = "http://44.211.175.160:";
    public static void main(String... args){
        port(getPort());
        //Defining the route from static files
        Spark.staticFiles.location("/static");
        //Header access to static file
        Spark.staticFiles.header("Access-Control-Allow-Origin", "*");
        //configuration for request from front
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
        // More configuration for request from back
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.type("application/json");
        });
        //Endpoints
        path("/api/messages", () -> {
            post("", (req, res) -> {
                Integer indexPort = serverNumber % 3;
                serverNumber++;
                String server_endpoint = baseURL + servers[indexPort] + "/api/messages";
                HttpConnection.setMethod("POST");
                HttpConnection.setGetUrl(server_endpoint);
                String responseJson = HttpConnection.doRequestPost(req.body());
                return responseJson;
            });
            get("", (req, res) -> {
                Integer indexPort = serverNumber % 3;
                serverNumber++;
                String server_endpoint = baseURL + servers[indexPort] + "/api/messages";
                HttpConnection.setMethod("GET");
                HttpConnection.setGetUrl(server_endpoint);
                String responseJson = HttpConnection.doRequestGet();
                return responseJson;
            });;
        });

    }

    /**
     * Method to get port from PORT ENV variable
     * @return int with the number of port
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }

}