package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import platform.DTO.CodeResponseDTO;
import platform.DTO.HiddenCodeResponseDTO;
import platform.interfaces.CodeService;
import platform.interfaces.HiddenCodeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
public class CodeController {
    private final CodeService codeService;
    private final HiddenCodeService hiddenCodeService;

    @Autowired
    public CodeController(CodeService codeService, HiddenCodeService hiddenCodeService) {
        this.codeService = codeService;
        this.hiddenCodeService = hiddenCodeService;
    }

//    @GetMapping("")
    public String getCode(Model model) {
        CodeResponseDTO codeResponseDTO = codeService.getCode();
        model.addAttribute("codeSnippet", codeResponseDTO.getCode());
        model.addAttribute("loadDate", codeResponseDTO.getDate());
        return "code";
    }

    @GetMapping("/new")
    public String getFormPage() {
        return "forward:/create.html";
    }

    @GetMapping("/{id}")
    public String getFormPage(@PathVariable String id, Model model) {
        UUID uuid = UUID.fromString(id);

        Optional<CodeResponseDTO> code = Optional.ofNullable(codeService.getCodeById(uuid));
        if (code.isPresent()){
            model.addAttribute("snippet", code.get());
            return "code";
        }

        Optional<HiddenCodeResponseDTO> hiddenCode = Optional.ofNullable(hiddenCodeService.getCodeById(uuid));
        if (hiddenCode.isPresent()){
            model.addAttribute("snippet", hiddenCode.get());
        }
        return "code";
    }

    @GetMapping("/latest")
    public String getLatestCode(Model model) {
        List<CodeResponseDTO> code = codeService.getLatestCodeSnippets();
        model.addAttribute("snippets", code);
        return "latest";
    }

}
