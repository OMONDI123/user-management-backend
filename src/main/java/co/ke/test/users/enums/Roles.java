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
public enum Roles implements DescribedEnum{
    ADMIN("ADMIN", "Admin"),
    USER("USER", "User");
    

    private final String value;
    private final String description;

    Roles(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() { return value; }
    public String getDescription() { return description; }

    public static Roles fromValue(String value) {
        for (Roles e : Roles.values()) {
            if (e.value.equalsIgnoreCase(value) || e.description.equalsIgnoreCase(value)) return e;
        }
        throw new IllegalArgumentException("Unknown Roles: " + value);
    }

    @JsonCreator
    public static Roles forValues(@JsonProperty("value") String value, @JsonProperty("description") String description) {
        for (Roles e : Roles.values()) {
            if (e.value.equalsIgnoreCase(value) || e.description.equalsIgnoreCase(value)) return e;
        }
        return null;
    }
}
