package infoshare.springdatamongodborder.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "collection")
public class TestObject {
    @Id
    private ObjectId id;
    private String name;
    private NestedObject nested;
    private boolean active;

    public TestObject(String name, NestedObject nested, boolean active) {
        this.name = name;
        this.nested = nested;
        this.active = active;
    }
}
