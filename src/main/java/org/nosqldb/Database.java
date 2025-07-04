package org.nosqldb;

import java.util.HashMap;
import java.util.Map;

public class Database {
    String name;
    Map<String, Collection> collections;
    Map<String, String> metadata;

    public Database(String name) {
        this.name = name;
        this.collections = new HashMap<>();
    }

    public Database(String name, Map<String, Collection> collections) {
        this.name = name;
        this.collections = collections;
    }

    public Map<String, Collection> getCollections() {
        return collections;
    }

    public void setCollections(Map<String, Collection> collections) {
        this.collections = collections;
    }

    public void addCollection(String name, Collection collection) {
        this.collections.put(name, collection);
    }

    public boolean deleteCollection(String name) {
        return this.collections.remove(name) != null;
    }
}
