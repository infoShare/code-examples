package infoshare.mongodbindexes.init;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import infoshare.mongodbindexes.config.MongoClientConfig;
import infoshare.mongodbindexes.indexes.*;
import infoshare.mongodbindexes.model.City;
import infoshare.mongodbindexes.model.IndexField;
import infoshare.mongodbindexes.model.Monument;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer {

    private static final String CITIES_COLLECTION = "cities";

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        MongoDatabase database = MongoClientConfig.getMongoDatabase();
        MongoCollection<City> collection = database.getCollection(CITIES_COLLECTION, City.class);
        if (collection.countDocuments() == 0) {
            createCities(collection);
        }
    }

    private void createCities(MongoCollection<City> collection) {
        collection.insertMany(cities());
        collection.createIndex(new SingleFieldIndex(new IndexField("code", true)).createIndex(), new IndexOptions().unique(true));
        collection.createIndex(new CompoundIndex(List.of(new IndexField("name", true),
                        new IndexField("country", true))).createIndex());
        collection.createIndex(new MultikeyIndex(new IndexField("tags", true)).createIndex());
        collection.createIndex(new WildcardIndex(new IndexField("monuments", true)).createIndex());
        collection.createIndex(new TextIndex("description").createIndex(), new IndexOptions().collation(Collation.builder().locale("simple").collationStrength(CollationStrength.SECONDARY).build()));
        collection.createIndex(new HashedIndex("population").createIndex());
        collection.createIndex(new Geo2dIndex("location").createIndex());
        collection.createIndex(new Geo2dSphereIndex(List.of("monuments.location")).createIndex(),
                new IndexOptions().partialFilterExpression(Filters.exists("monuments")));
    }

    private List<City> cities() {
        return List.of(City.builder()
                        .code("WAW")
                        .name("Warsaw")
                        .description("Capital of Poland")
                        .region("Masovian Voivodeship")
                        .country("Poland")
                        .population("1798000")
                        .location(List.of(52.237049, 21.017532))
                        .tags(List.of("Capital", "Old Town", "Big City"))
                        .monuments(List.of(Monument.builder()
                                .name("PKiN")
                                .architect("Lev Rudnev")
                                .constructionYear(1952)
                                .location(new Point(new Position(52.231958, 21.005853)))
                                .build()))
                        .build(),
                City.builder()
                        .code("KRK")
                        .name("Krakow")
                        .description("Former capital of Poland")
                        .region("Lesser Poland Voivodeship")
                        .country("Poland")
                        .population("779115")
                        .location(List.of(50.049683, 19.944544))
                        .tags(List.of("Old Town", "Big City"))
                        .monuments(List.of(Monument.builder()
                                .name("Wawel")
                                .architect("Jakub Szczetny")
                                .constructionYear(1370)
                                .location(new Point(new Position(50.054722, 19.935833)))
                                .build()))
                        .build(),
                City.builder().code("WRO")
                        .name("Wroclaw")
                        .region("Lower Silesian Voivodeship")
                        .country("Poland")
                        .population("631235")
                        .location(List.of(51.107883, 17.038538))
                        .monuments(List.of(Monument.builder()
                                .name("Centennial Hall")
                                .architect("Max Berg")
                                .constructionYear(1913)
                                .location(new Point(new Position(51.106944, 17.071111)))
                                .build()))
                        .build(),
                City.builder()
                        .code("GDN")
                        .name("Gdansk")
                        .region("Pomeranian Voivodeship")
                        .country("Poland")
                        .population("464254")
                        .location(List.of(54.352025, 18.646638))
                        .build(),
                City.builder()
                        .code("POZ")
                        .name("Poznan")
                        .region("Greater Poland Voivodeship")
                        .country("Poland")
                        .population("538633")
                        .location(List.of(52.406376, 16.925167))
                        .build(),
                City.builder()
                        .code("KAT")
                        .name("Katowice")
                        .region("Silesian Voivodeship")
                        .country("Poland")
                        .population("294510")
                        .location(List.of(50.264892, 19.023782))
                        .build(),
                City.builder()
                        .code("LUB")
                        .name("Lublin")
                        .region("Lublin Voivodeship")
                        .country("Poland")
                        .population("339784")
                        .location(List.of(51.246452, 22.568445))
                        .build());
    }
}
