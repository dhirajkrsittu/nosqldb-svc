package org.nosqldb;

public class QueryPart {
    String key;
    Object value;

    public QueryPart(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
