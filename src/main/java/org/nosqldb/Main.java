package org.nosqldb;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void mainTest(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }

    public static void main(String[] args) {
        Database db = new Database("test");
        Collection collection = new Collection("test1");
        db.addCollection(collection.getName(), collection);

        Map<String, EntryKey> schema = new HashMap<>();
        schema.put("name", new EntryKey("name", DataType.STRING));
        schema.put("email", new EntryKey("email", DataType.STRING));
        schema.put("contact", new EntryKey("contact", DataType.INTEGER));

        collection.addIndex("name_index", "name");

        Document doc = new Document(schema);
        doc.upsertEntry("name", "dhiraj");
        doc.upsertEntry("email", "dhiraj@gmail.com");
        doc.upsertEntry("contact", 1234);

        collection.addDocument(doc);

        LinkedHashMap<String, Object> searchQuery = new LinkedHashMap<>();
        searchQuery.put("name", "dhiraj");
        searchQuery.put("contact", 1234);
        List<Document> docs = collection.search(searchQuery);

        for (Document document : docs) {
            document.printDoc();
        }

//        List<Document> docs2 = collection.search(searchQuery);
//
//        for (Document document : docs2) {
//            document.printDoc();
//        }
    }
}