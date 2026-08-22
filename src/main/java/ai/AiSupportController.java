package ai;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiSupportController {

    private final AiSupportService aiSupportService;

    public AiSupportController(
            AiSupportService aiSupportService) {

        this.aiSupportService = aiSupportService;
    }

    @PostMapping("/support")
    public String support(
            @RequestBody AiRequest request) {

        return aiSupportService.generateResponse(
                request.getMessage()
        );
    }
}