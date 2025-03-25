package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.DTO.ClientRequestDTO;
import platform.DTO.HiddenCodeResponseDTO;
import platform.interfaces.HiddenCodeService;
import platform.mapper.MyMapper;
import platform.model.HiddenCode;
import platform.repository.HiddenCodeRepository;
import platform.utils.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class HiddenCodeServiceImpl implements HiddenCodeService {

private final MyMapper myMapper;
private final HiddenCodeRepository hiddenCodeRepository;

@Autowired
public HiddenCodeServiceImpl(MyMapper myMapper, HiddenCodeRepository hiddenCodeRepository) {
    this.myMapper = myMapper;
    this.hiddenCodeRepository = hiddenCodeRepository;
}

    @Override
    public String saveCode(ClientRequestDTO code) {
        //create object model and assign value to the object model
        HiddenCode hiddenCode = myMapper.convertToHiddenCode(code);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        hiddenCode.setDate(timestamp);
        hiddenCode.setExpired(Util.parseStringToDate(timestamp).plusSeconds(code.getTime()));
        //save to repo
        hiddenCodeRepository.save(hiddenCode);
        return hiddenCode.getId().toString();
    }

    @Override
    public HiddenCodeResponseDTO getCodeById(UUID id) {
        Optional<HiddenCode> code = hiddenCodeRepository.findById(id);

        //checkViewLimit
        code = checkAndUpdateViewLimit(code);

        //checkTImeLimit
        long seconds = ChronoUnit.SECONDS.between(
                LocalDateTime.now(), code.get().getExpired());
        code.get().setTime(seconds);
        if (seconds <= 0) {
            hiddenCodeRepository.delete(code.get());
            return null;
        }

        if (code.get().getViews() <0) {
            hiddenCodeRepository.delete(code.get());
            return null;
        }

        HiddenCodeResponseDTO responseDTO = code.map(hiddenCode -> myMapper.convertToHiddenCodeDTO(hiddenCode))
                .orElse(null);
        return responseDTO;
    }

    private Optional<HiddenCode> checkAndUpdateViewLimit(Optional<HiddenCode> code){
            code.get().setViews(code.get().getViews() - 1);
            hiddenCodeRepository.save(code.get());
        return code;
    }
}


