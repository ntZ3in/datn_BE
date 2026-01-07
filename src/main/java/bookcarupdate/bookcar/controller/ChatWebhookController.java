package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatWebhookController {

    private final ChatService chatService;

    public ChatWebhookController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/webhook")
    public Map<String, Object> handleDialogflowWebhook(@RequestBody Map<String, Object> payload) {
        // 1. Trích xuất tin nhắn từ Dialogflow
        Map<String, Object> queryResult = (Map<String, Object>) payload.get("queryResult");
        String userMessage = (String) queryResult.get("queryText");

        // 2. Lấy email (Tạm thời fix cứng hoặc lấy từ session/metadata nếu có)
        String userEmail = "khachhang@example.com";

        // 3. Gọi ChatService xử lý (truy vấn SQL + OpenAI)
        String botReply = chatService.chat(userMessage, userEmail);

        // 4. Trả về đúng cấu hình Dialogflow yêu cầu
        return Map.of("fulfillmentText", botReply);
    }
}
