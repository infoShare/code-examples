package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

/*
    Indeks wspierający wyszukiwanie po punktach geograficznych (2 wymiarowych)
 */
public class Geo2dIndex implements Index {

    private final String field;

    public Geo2dIndex(String field) {
        this.field = field;
    }

    @Override
    public Bson createIndex() {
        return Indexes.geo2d(field);
    }
}
