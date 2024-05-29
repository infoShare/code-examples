package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

/*
    Indeks wspierający wyszukiwanie tekstu
 */
public class TextIndex implements Index {

    private final String field;

    public TextIndex(String field) {
        this.field = field;
    }

    @Override
    public Bson createIndex() {
        return Indexes.text(field);
    }
}
