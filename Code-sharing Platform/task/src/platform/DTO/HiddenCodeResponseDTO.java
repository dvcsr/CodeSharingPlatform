package platform.DTO;

import lombok.Data;

@Data
public class HiddenCodeResponseDTO {
    private String code;
    private String date;
    private Long time;
    private Long views;
}
