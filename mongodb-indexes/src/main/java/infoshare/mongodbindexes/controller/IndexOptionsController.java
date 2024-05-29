package infoshare.mongodbindexes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("api/options")
@Tag(name = "IndexOptionsController", description = "IndexOptionsController")
public class IndexOptionsController {

    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    @Operation(summary = "Options/Properties")
    public String options() {
        // Unikalne wartosci dla wybranego pola/pól
        IndexOptions unique = new IndexOptions().unique(true);
        // specyficznye dla języka reguły porównywania ciągów, takich jak reguły dotyczące liter i znaków akcentu.
        IndexOptions collation = new IndexOptions().collation(Collation.builder().locale("en").build());
        // strength = 1, 2 - case-insensitive index
        IndexOptions caseInsensitive = new IndexOptions().collation(Collation.builder().locale("en").collationStrength(CollationStrength.SECONDARY).build());
        // zaindeksowane tylko te dokumenty pasujace do podanego filtra
        IndexOptions partial = new IndexOptions().partialFilterExpression(Filters.eq("country", "Poland"));
        //zawieraja tylko obiekty posiadajace zaindeksowane pola
        IndexOptions sparse = new IndexOptions().sparse(true);
        //automatycznie usuwanie dokumentow po czasie zdefiniowanym w definicji indeksu
        IndexOptions expire = new IndexOptions().expireAfter(30L, TimeUnit.DAYS);
        //niewidoczne w query plannerze oraz nie wspierajace zapytan. Moga byc uzywane do badania wplywu usuniecia indeksu.
        IndexOptions hidden = new IndexOptions().hidden(true);
        //budowanie indeksów odbywa się w tle (mozliwe zapisy/odczyty danych podczas tworzenia indeksu)
        IndexOptions background = new IndexOptions().background(true);

        IndexOptions named = new IndexOptions().name("indexName");
        IndexOptions weights = new IndexOptions().weights(new Document(Map.of("name", 1, "country", 5)));
        IndexOptions defaultLanguage = new IndexOptions().defaultLanguage("english");
        IndexOptions languageOverride = new IndexOptions().languageOverride("language");
        IndexOptions textVersion = new IndexOptions().textVersion(3);
        IndexOptions sphereVersion = new IndexOptions().sphereVersion(3);
        IndexOptions bits = new IndexOptions().bits(3);
        IndexOptions min = new IndexOptions().min(1.0);
        IndexOptions max = new IndexOptions().max(100.0);
        IndexOptions storageEngine = new IndexOptions().storageEngine(new Document("inMemory", ""));
        IndexOptions version = new IndexOptions().version(3);

        return """
                Unique: %s
                Collation: %s
                CaseInsensitive: %s
                Partial: %s
                Sparse: %s
                Expire: %s
                Named: %s
                Background: %s
                Hidden: %s
                Weights: %s
                DefaultLanguage: %s
                LanguageOverride: %s
                TextVersion: %s
                SphereVersion: %s
                Bits: %s
                Min: %s
                Max: %s
                StorageEngine: %s
                Version: %s
                """.formatted(
                convertIndexOptionToString(unique),
                convertIndexOptionToString(collation),
                convertIndexOptionToString(caseInsensitive),
                convertIndexOptionToString(partial),
                convertIndexOptionToString(sparse),
                convertIndexOptionToString(expire),
                convertIndexOptionToString(named),
                convertIndexOptionToString(background),
                convertIndexOptionToString(hidden),
                convertIndexOptionToString(weights),
                convertIndexOptionToString(defaultLanguage),
                convertIndexOptionToString(languageOverride),
                convertIndexOptionToString(textVersion),
                convertIndexOptionToString(sphereVersion),
                convertIndexOptionToString(bits),
                convertIndexOptionToString(min),
                convertIndexOptionToString(max),
                convertIndexOptionToString(storageEngine),
                convertIndexOptionToString(version));
    }

    @SneakyThrows
    private String convertIndexOptionToString(IndexOptions indexOptions) {
        return objectMapper.writeValueAsString(indexOptions);
    }

}
