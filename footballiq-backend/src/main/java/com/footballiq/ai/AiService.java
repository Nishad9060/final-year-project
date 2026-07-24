package com.footballiq.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Value("${gemini.api.key:}")
    private String apiKey;

    public String generateTacticalSummary(String matchId, String matchDataJson) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Simulated AI Tactical Summary: Based on the data, the home team has dominated possession in the final third, creating numerous high xG opportunities, while the away team relies on swift counter-attacks.";
        }
        
        // TODO: In a production scenario, use the Google GenAI SDK
        // e.g., GenerativeModel model = new GenerativeModel("gemini-1.5-pro", apiKey);
        // GenerateContentResponse response = model.generateContent("Analyze this match data: " + matchDataJson);
        // return response.text();
        
        return "Tactical summary generated from Gemini API.";
    }

    public String chatWithAssistant(String userMessage) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Simulated AI Assistant: I am FootballIQ's AI assistant. You asked: '" + userMessage + "'. I can help you analyze player stats and team formations!";
        }
        
        return "Response from Gemini API.";
    }
}
