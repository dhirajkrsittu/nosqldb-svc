package org.nosqldb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Index {
    String name;
    String column; // refers to the key name in the documents
    // List<String> keys
    Map<String, List<Document>> documents;
    // {}
    // {"name": "dhiraj", "email":"", "abc":true}
    // {name: dhiraj}
    // {name: dhiraj}

    public Index(String name, String column, Map<String, List<Document>> documents) {
        this.name = name;
        this.column = column;
        this.documents = documents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Map<String, List<Document>> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, List<Document>> documents) {
        this.documents = documents;
    }

    public void addDocument(Document doc) {
        String key = doc.getEntries().containsKey(this.column) ?
                doc.getEntries().get(this.column).toString() : null;
        if (key != null) {
            this.documents.putIfAbsent(key, new ArrayList<>());
            this.documents.get(key).add(doc);
        }
    }
}
