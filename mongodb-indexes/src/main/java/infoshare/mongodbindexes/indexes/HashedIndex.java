package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

/*
    Indeks uzywajacy wyniku funkcji hashującej danego pola jako wartosci
 */
public class HashedIndex implements Index {

    private final String field;

    public HashedIndex(String field) {
        this.field = field;
    }

    @Override
    public Bson createIndex() {
        return Indexes.hashed(field);
    }
}
