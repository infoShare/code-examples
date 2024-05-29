package infoshare.mongodbindexes.indexes;

import com.mongodb.client.model.Indexes;
import infoshare.mongodbindexes.model.IndexField;
import org.bson.conversions.Bson;
import org.springframework.util.StringUtils;

/*
    Indeks wspierający wyszukiwanie po dowolnym lub nieznanym polu
 */
public class WildcardIndex implements Index {

    private final IndexField field;

    public WildcardIndex(IndexField field) {
        this.field = field;
    }

    @Override
    public Bson createIndex() {
        String fieldExpr = StringUtils.hasText(field.getField()) ? field.getField() + ".$**" : "$**";
        return field.isAscending() ? Indexes.ascending(fieldExpr) : Indexes.descending(fieldExpr);
    }

}
