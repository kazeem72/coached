// UserRepresentation.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package ng.com.justjava.coached.entity;
import java.util.List;

public class UserRepresentation {
    private String firstName;
    private Attributes attributes;
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String lastName;

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public Attributes getAttributes() { return attributes; }
    public void setAttributes(Attributes value) { this.attributes = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}

// Attributes.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

