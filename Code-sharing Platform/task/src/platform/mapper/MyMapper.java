package platform.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import platform.DTO.ClientRequestDTO;
import platform.DTO.CodeResponseDTO;
import platform.DTO.HiddenCodeResponseDTO;
import platform.model.HiddenCode;
import platform.model.UserCode;

@Component
public class MyMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public MyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CodeResponseDTO convertToUserCodeDTO(UserCode code) {
        return modelMapper.map(code, CodeResponseDTO.class);
    }

    public UserCode convertToUserCode(ClientRequestDTO dto) {
        return modelMapper.map(dto, UserCode.class);
    }

    public HiddenCodeResponseDTO convertToHiddenCodeDTO(HiddenCode code) {
        return modelMapper.map(code, HiddenCodeResponseDTO.class);
    }
    public HiddenCode convertToHiddenCode(ClientRequestDTO dto) {
        return modelMapper.map(dto, HiddenCode.class);
    }
}