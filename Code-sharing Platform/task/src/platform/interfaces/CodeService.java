package platform.interfaces;

import platform.DTO.ClientRequestDTO;
import platform.DTO.CodeResponseDTO;
import platform.model.UserCode;

import java.util.List;
import java.util.UUID;

public interface CodeService {
    String saveCode(ClientRequestDTO code);

    Long generateId();

    CodeResponseDTO getCode();
    CodeResponseDTO getCodeById(UUID id);
    List<CodeResponseDTO> getLatestCodeSnippets();
}
