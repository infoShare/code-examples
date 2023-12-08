package infoshare.springdatamongodborder.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NestedObject {
    private String systemName;
    private String checkName;
    private String checkVersion;
    private String checkCategory;
}
