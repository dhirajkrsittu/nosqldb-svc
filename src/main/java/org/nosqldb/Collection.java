package org.nosqldb;

import java.util.*;

public class Collection {
    String name;
    Map<String, Document> documents;
    Map<String, Index> indexes;

    public Collection(String name) {
        this.name = name;
        this.documents = new HashMap<>();
        this.indexes = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Document> getDocuments() {
        return documents;
    }

//    public void setEntries(Map<String, Entry> entries) {
//        this.entries = entries;
//    }

    public void addDocument(Document doc) {
        // add uniqueness check
        this.documents.put(UUID.randomUUID().toString(), doc);
        for (String key : doc.entries.keySet()) {
            if (indexes.containsKey(key)) {
                indexes.get(key).addDocument(doc);
            }
        }
    }

    public Document getDocument(String id) {
        return this.documents.get(id);
    }

    public List<Document> search(LinkedHashMap<String, Object> query) {
        List<QueryPart> queryParts = new ArrayList<>();
        for (String key : query.keySet()) {
            queryParts.add(new QueryPart(key, query.get(key)));
        }

        return searchRec(queryParts, this.documents.values().stream().toList(), 0);
    }

    private List<Document> searchRec(List<QueryPart> queryParts, List<Document> docs, int qi) {
        if (qi >= queryParts.size()) {
            return docs;
        }

        QueryPart queryPart = queryParts.get(qi);
        String key = queryPart.key;
        if (indexes.containsKey(key)) {
            List<Document> filtered = indexes.get(key).documents.get(queryPart.value);
            return searchRec(queryParts, filtered, qi + 1);
        } else {
            List<Document> filtered = new ArrayList<>();
            for (Document doc : docs) {
                if (doc.getEntries().containsKey(key) && doc.getEntries().get(key) == queryPart.value) {
                    filtered.add(doc);
                }
            }
            return searchRec(queryParts, filtered, qi + 1);
        }
    }

    // name, email -> CI
    // query -> {name: test, email: "abc@test.com"}
    // name or name, email

    public void addIndex(String name, String key) {
        Map<String, List<Document>> docs = new HashMap<>();
        for (Document doc : this.documents.values()) {
            docs.putIfAbsent(doc.getEntries().get(key).toString(), new ArrayList<>());
            docs.get(doc.getEntries().get(key)).add(doc);
        }
        Index index = new Index(name, key, docs);
        this.indexes.put(key, index);
    }

}
