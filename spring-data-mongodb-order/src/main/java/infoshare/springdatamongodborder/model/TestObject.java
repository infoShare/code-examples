package infoshare.springdatamongodborder.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "collection")
public class TestObject {
    @Id
    private ObjectId id;
    private String name;
    private NestedObject nested;
    private boolean active;
}
