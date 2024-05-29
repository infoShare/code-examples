package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import infoshare.mongodbindexes.model.IndexField;
import org.bson.conversions.Bson;

/*
    Pojedyncze pola z dokumentu które jest listą (osobny wpis w indeksie dla kazdego elementu)
 */
public class MultikeyIndex implements Index {

    private final IndexField field;

    public MultikeyIndex(IndexField field) {
        this.field = field;
    }

    @Override
    public Bson createIndex() {
        return field.isAscending() ? Indexes.ascending(field.getField()) : Indexes.descending(field.getField());
    }

}
