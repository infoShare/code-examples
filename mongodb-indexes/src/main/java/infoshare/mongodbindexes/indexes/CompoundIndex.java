package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import infoshare.mongodbindexes.model.IndexField;
import org.bson.conversions.Bson;

import java.util.List;

/*
    Indeks operujący na kilku polach dokumentu
 */
public class CompoundIndex implements Index {

    private final List<IndexField> fields;

    public CompoundIndex(List<IndexField> fields) {
        this.fields = fields;
    }

    @Override
    public Bson createIndex() {
        return Indexes.compoundIndex(fields.stream().map(this::convert).toList());
    }

    private Bson convert(IndexField field) {
        return field.isAscending() ? Indexes.ascending(field.getField()) : Indexes.descending(field.getField());
    }
}
