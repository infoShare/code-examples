package infoshare.mongodbindexes.controller;

import infoshare.mongodbindexes.indexes.*;
import infoshare.mongodbindexes.model.IndexField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bson.conversions.Bson;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/creator")
@Tag(name = "IndexCreatorController", description = "IndexCreatorController")
public class IndexCreatorController {

    @PostMapping("/single")
    @Operation(summary = "SingleIndex")
    public String single(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Index field", required = true)
                         @Valid @RequestBody IndexField field) {
        return convertIndexToString(new SingleFieldIndex(field).createIndex());
    }

    @PostMapping("/compound")
    @Operation(summary = "CompoundIndex")
    public String compound(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List of index fields", required = true)
                           @RequestBody List<IndexField> fields) {
        return convertIndexToString(new CompoundIndex(fields).createIndex());
    }

    @GetMapping("/hashed")
    @Operation(summary = "HashedIndex")
    public String hashed(@Parameter(name = "field", description = "field", required = true)
                         @RequestParam(name = "field") String field) {
        return convertIndexToString(new HashedIndex(field).createIndex());
    }

    @GetMapping("/text")
    @Operation(summary = "TextIndex")
    public String text(@Parameter(name = "field", description = "field", required = true)
                       @RequestParam(name = "field") String field) {
        return convertIndexToString(new TextIndex(field).createIndex());
    }

    @GetMapping("/geo2d")
    @Operation(summary = "Geo2dIndex")
    public String geo2d(@Parameter(name = "field", description = "field", required = true)
                        @RequestParam(name = "field") String field) {
        return convertIndexToString(new Geo2dIndex(field).createIndex());
    }

    @GetMapping("/geo2dSphere")
    @Operation(summary = "Geo2dSphereIndex")
    public String geo2dSphere(@Parameter(name = "fields", description = "fields", required = true)
                              @RequestParam(name = "fields") List<String> fields) {
        return convertIndexToString(new Geo2dSphereIndex(fields).createIndex());
    }

    @GetMapping("/wildcard")
    @Operation(summary = "Wildcard")
    public String wildcard(@Parameter(name = "field", description = "field", required = true)
                           @RequestParam(name = "field") IndexField field) {
        return convertIndexToString(new WildcardIndex(field).createIndex());
    }

    private static String convertIndexToString(Bson index) {
        return index.toBsonDocument().toJson();
    }
}
