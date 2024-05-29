package infoshare.mongodbindexes.indexes;

import org.bson.conversions.Bson;

public interface Index {
    Bson createIndex();
}
