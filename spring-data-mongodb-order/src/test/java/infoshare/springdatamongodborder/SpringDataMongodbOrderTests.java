package infoshare.springdatamongodborder;

import infoshare.springdatamongodborder.model.NestedObject;
import infoshare.springdatamongodborder.model.TestObject;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest(properties = {"de.flapdoodle.mongodb.embedded.version=4.4.9"})
class SpringDataMongodbOrderTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testIncorrectFieldsOrderQuery() {
        //given
        NestedObject nested = NestedObject.builder().systemName("A").checkName("B").checkVersion("1").build();
        TestObject testObject = mongoTemplate.insert(TestObject.builder().name("TEST").nested(nested).active(true).build());
        LinkedHashMap<Object, String> nestedProps = new LinkedHashMap<>();
        nestedProps.put("systemName", "A");
        nestedProps.put("checkName", "B");
        nestedProps.put("checkVersion", "1");
        Map<String, LinkedHashMap<Object, String>> queryMap = Map.of("nested", nestedProps);
        //when
        List<TestObject> testObjects = mongoTemplate.find(new BasicQuery(new Document(queryMap)), TestObject.class);
        //then
        /*
        Failing as query for Mongodb is:
        { "nested" : { "checkName" : "B", "checkVersion" : "1", "systemName" : "A" } }
        instead of
        { "nested" : { "systemName" : "A", "checkName" : "B", "checkVersion" : "1" } }
        on
        MongoTemplate.doFind() on line: Document mappedQuery = queryContext.getMappedQuery(entity);
        */
        assertEquals(1, testObjects.size());
        assertEquals(testObject, testObjects.getFirst());
    }

}
