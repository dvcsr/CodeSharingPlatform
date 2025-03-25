package platform.DTO;

import lombok.Data;

@Data
public class CodeResponseDTO {
    private String code;
    private String date;
    private Long time = 0L;
    private Long views = 0L;
}
