package infoshare.mongodbindexes.model;

import com.mongodb.client.model.geojson.Point;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Monument {
    private String name;
    private String architect;
    private Integer constructionYear;
    private Point location;
}
