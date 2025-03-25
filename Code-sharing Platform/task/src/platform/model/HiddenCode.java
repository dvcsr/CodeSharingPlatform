package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class HiddenCode {
    @Id
    @Column(columnDefinition = "UUID")
    @JsonIgnore
    private UUID id = UUID.randomUUID();
    @Column
    private String code;
    @Column
    private String date;
    @Column
    private Long time;
    @Column
    private LocalDateTime expired;
    @Column
    private boolean isViewedOnce;
    @Column
    private Long views;


    public HiddenCode() {}

    public HiddenCode(String code, String date, Long duration, Long viewcount) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
        this.isViewedOnce = false;
    }

}