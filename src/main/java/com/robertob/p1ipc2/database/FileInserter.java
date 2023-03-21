package com.robertob.p1ipc2.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robertob.p1ipc2.model.Product;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInserter {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode rootNode;
    private Connection connection;

    private Map<String, String> fieldMapping = new HashMap<>();


    public FileInserter(String jsonString, Connection connection) throws JsonProcessingException {
        rootNode = this.objectMapper.readTree(jsonString);
        this.connection = connection;
        createMappings();
        fillDatabase();
    }

    private void createMappings() {
        fieldMapping.put("producto", "name");
        fieldMapping.put("precio", "price");
        fieldMapping.put("stock", "stock");
    }

    public void fillDatabase() throws JsonProcessingException {
        DbProduct dbProduct = new DbProduct(connection);
        List<Product> products = objectMapper.readValue(
                this.rootNode.path("productos").toString(),
                new TypeReference<List<Product>>() {}
        );

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
