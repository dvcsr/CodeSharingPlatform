package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class UserCode {
    @Id
    @Column(columnDefinition = "UUID")
    @JsonIgnore
    private UUID id = UUID.randomUUID();
    @Column
    private String code;
    @Column
    private String date;

    public UserCode() {}

    public UserCode(String code, String date) {
        this.code = code;
        this.date = date;
    }
}
