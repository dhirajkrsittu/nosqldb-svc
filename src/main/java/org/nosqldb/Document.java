package org.nosqldb;

/*
{"a": 1, "b": {"c": true}}
{"a": 1, "b": {"c": true}}
{"a": 1, "b": {"c": true}}

 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    Map<String, EntryKey> schema;
    Map<String, Object> entries;

    public Document(Map<String, EntryKey> schema) {
        this.schema = schema;
        this.entries = new HashMap<>();
    }

    public Map<String, EntryKey> getSchema() {
        return schema;
    }

    public void setSchema(Map<String, EntryKey> schema) {
        this.schema = schema;
    }

    public Map<String, Object> getEntries() {
        return entries;
    }

//    public void setEntries(Map<String, Object> entries) {
//        this.entries = entries;
//    }

    public void upsertEntry(String key, Object value) {
        EntryKey keySchema = this.schema.get(key);
        if (keySchema != null) {
            DataType dataType = keySchema.getDataType();    // not needed??
            if (isValidDataType(value)) {
                this.entries.put(key, value);
            }
        }
    }

    public boolean removeEntry(String key) {
        return this.entries.remove(key) != null;
    }

    private boolean isValidDataType(Object value) {
        return value instanceof Integer || value instanceof String ||
                value instanceof List<?> || value instanceof Map<?,?>;
    }

    public void printDoc() {
        for (String key : entries.keySet()) {
            System.out.print(key + ": " + entries.get(key) + ", ");
        }
        System.out.println();
    }
}
