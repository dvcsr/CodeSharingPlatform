package platform.interfaces;

import platform.DTO.ClientRequestDTO;
import platform.DTO.HiddenCodeResponseDTO;


import java.util.UUID;

public interface HiddenCodeService {
    String saveCode(ClientRequestDTO code);
    HiddenCodeResponseDTO getCodeById(UUID id);
}
