package infoshare.mongodbindexes.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoClientConfig {

    private static final String MONGO_URI = "mongodb://root:pass@localhost:27017/?authSource=admin";
    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getMongoDatabase() {
        if (mongoDatabase == null) {
            MongoClient mongoClient = MongoClients.create(MONGO_URI);
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
            mongoDatabase = mongoClient.getDatabase("db").withCodecRegistry(pojoCodecRegistry);
        }
        return mongoDatabase;
    }

}
