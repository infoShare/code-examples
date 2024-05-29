package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

import java.util.List;

/*
    Indeks wspierający wyszukiwanie po punktach geograficznych (2 wymiarowych) dotyczących kuli ziemskiej, np:
    - określic punkty w danym obszarze
    - obliczyć odległość do określonego punktu
    - zwracać dokładne dopasowania w zapytaniach o współrzędne
 */
public class Geo2dSphereIndex implements Index {
    private final List<String> fields;

    public Geo2dSphereIndex(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public Bson createIndex() {
        return Indexes.geo2dsphere(fields);
    }
}
