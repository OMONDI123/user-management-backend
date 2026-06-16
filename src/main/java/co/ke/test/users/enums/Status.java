/**
 * 
 */
package co.ke.test.users.enums;

/**
 * 
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status implements DescribedEnum{
    ACTIVE("ACTIVE", "Active"),
    INACTIVE("INACTIVE", "Inactive");
    

    private final String value;
    private final String description;

    Status(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() { return value; }
    public String getDescription() { return description; }

    public static Status fromValue(String value) {
        for (Status e : Status.values()) {
            if (e.value.equalsIgnoreCase(value) || e.description.equalsIgnoreCase(value)) return e;
        }
        throw new IllegalArgumentException("Unknown Status: " + value);
    }

    @JsonCreator
    public static Status forValues(@JsonProperty("value") String value, @JsonProperty("description") String description) {
        for (Status e : Status.values()) {
            if (e.value.equalsIgnoreCase(value) || e.description.equalsIgnoreCase(value)) return e;
        }
        return null;
    }
}
