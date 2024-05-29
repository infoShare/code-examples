package infoshare.mongodbindexes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndexField {
    private String field;
    private boolean ascending;
}
