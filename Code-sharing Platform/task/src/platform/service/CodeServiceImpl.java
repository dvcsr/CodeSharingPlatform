package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import platform.DTO.ClientRequestDTO;
import platform.DTO.CodeResponseDTO;
import platform.interfaces.CodeService;
import platform.mapper.MyMapper;
import platform.model.UserCode;
import platform.repository.CodeRepository;
import platform.utils.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CodeServiceImpl implements CodeService {
    //production grade inject repository, here we inject in-memory collections.
    private List<UserCode> codeRepositori = Collections.synchronizedList(new ArrayList<>());
    private static final int MAXLATEST   = 10;
    private final MyMapper myMapper;
    private final CodeRepository codeRepository;

    @Autowired
    public CodeServiceImpl(MyMapper myMapper, CodeRepository codeRepository) {
        this.myMapper = myMapper;
        this.codeRepository = codeRepository;
    }

    @Override
    public String saveCode(@RequestBody ClientRequestDTO code) {
        //create object model and assign value to the object model
        UserCode userCode = myMapper.convertToUserCode(code);
        userCode.setDate(Util.generateDateInString());

        //save to repo
        codeRepository.save(userCode);
        return userCode.getId().toString();
    }

    @Override
    public CodeResponseDTO getCode() {
        return myMapper.convertToUserCodeDTO(codeRepository.findAll().get(0));
    }

    @Override
    public CodeResponseDTO getCodeById(UUID id) {
        Optional<UserCode> code = codeRepository.findById(id);
        return code.map(userCode -> myMapper.convertToUserCodeDTO(userCode))
                .orElse(null);
    }

    @Override
    public Long generateId() {
        Long id = Long.valueOf(codeRepository.count());
        return id;
    }

    public List<CodeResponseDTO> getLatestCodeSnippets() {
//        List<UserCode> codeSnippetList = codeRepository.findTop10ByOrderByDateDesc(); //bug potent in test

        List<UserCode> latestListReversed = codeRepository.findAll();
        Collections.reverse(latestListReversed);

        List<UserCode> latestList = latestListReversed.size() > 10
                ? latestListReversed.subList(0, 10)
                : latestListReversed;

        List<CodeResponseDTO> responseDTOList = Collections.synchronizedList(new ArrayList<>());
        for (UserCode code : latestList) {
            responseDTOList.add(myMapper.convertToUserCodeDTO(code));
        }
        return responseDTOList;
    }

//    @PostConstruct
    private void init() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        String mockCode = "int myAge = 30;";
        UserCode mock = new UserCode(mockCode, timestamp);
        codeRepository.save(mock);
    }


}