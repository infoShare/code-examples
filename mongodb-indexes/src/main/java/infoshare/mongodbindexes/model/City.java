package infoshare.mongodbindexes.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class City {
    private String code;
    private String name;
    private String country;
    private String region;
    private String population;
    private String description;
    private List<Double> location;
    private List<String> tags;
    private List<Monument> monuments;
}
