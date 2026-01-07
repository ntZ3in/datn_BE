package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<?> chat(
            @RequestBody ChatRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            // Extract email from token if available
            String userEmail = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // In a real scenario, decode JWT to get email
                // For now, just pass null
                userEmail = null;
            }

            String response = chatService.chat(request.getMessage(), userEmail);
            return ResponseEntity.ok(new ChatResponse(response, true));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ChatResponse("Lỗi xử lý: " + e.getMessage(), false));
        }
    }

    public static class ChatRequest {
        private String message;

        public ChatRequest() {}

        public ChatRequest(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ChatResponse {
        private String response;
        private boolean success;

        public ChatResponse(String response, boolean success) {
            this.response = response;
            this.success = success;
        }

        public String getResponse() {
            return response;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
