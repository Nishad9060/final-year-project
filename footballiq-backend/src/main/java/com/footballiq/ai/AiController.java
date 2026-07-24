package com.footballiq.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@CrossOrigin(origins = "*") // Allow flutter web/mobile during dev
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/summary/{matchId}")
    public ResponseEntity<Map<String, String>> getTacticalSummary(
            @PathVariable String matchId,
            @RequestBody Map<String, Object> matchData) {
        
        String summary = aiService.generateTacticalSummary(matchId, matchData.toString());
        return ResponseEntity.ok(Map.of("summary", summary));
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chatWithAssistant(@RequestBody Map<String, String> request) {
        String userMessage = request.getOrDefault("message", "");
        String response = aiService.chatWithAssistant(userMessage);
        return ResponseEntity.ok(Map.of("response", response));
    }
}
