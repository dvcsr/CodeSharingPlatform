package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.DTO.ClientRequestDTO;
import platform.DTO.CodeResponseDTO;
import platform.DTO.HiddenCodeResponseDTO;
import platform.interfaces.CodeService;
import platform.interfaces.HiddenCodeService;
import platform.service.CodeServiceImpl;


import java.util.*;

@RestController
@RequestMapping("/api/code")
public class CodeRESTController {
    private final CodeService codeService;
    private final HiddenCodeService hiddenCodeService;

    @Autowired
    public CodeRESTController(CodeService codeService, HiddenCodeService hiddenCodeService) {
        this.codeService = codeService;
        this.hiddenCodeService = hiddenCodeService;
    }

//    @GetMapping("")
    public ResponseEntity<CodeResponseDTO> getCode (){
        CodeResponseDTO code = codeService.getCode(); //can be null

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(code);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> postCode(@RequestBody ClientRequestDTO code) {
        HashMap<String, String> response = new HashMap<>();
        if (code.getTime() <= 0 && code.getViews() <= 0){
            response.put("id", codeService.saveCode(code));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
        else {
            response.put("id", hiddenCodeService.saveCode(code));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getCodeById(@PathVariable String id) { //implement hiddencodeservice
        UUID uuid = UUID.fromString(id);
        Optional<CodeResponseDTO> code = Optional.ofNullable(codeService.getCodeById(uuid));
        if (code.isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(code.get());
        }
        Optional<HiddenCodeResponseDTO> hiddenCode = Optional.ofNullable(hiddenCodeService.getCodeById(uuid));
        if (hiddenCode.isPresent()){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(hiddenCode.get());
        }
        else return ResponseEntity.notFound().build();
        }


@GetMapping("/latest")
public ResponseEntity<List<CodeResponseDTO>> getLatestCode() {
    List<CodeResponseDTO> code = codeService.getLatestCodeSnippets();
    if (code.isEmpty()) return ResponseEntity.notFound().build();
    else return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(code);
}
}