package bookcarupdate.bookcar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.OrderRepository;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    //@Value("${openai.api-key}")
    //private String openaiApiKey;
    @Value("${openai.api-key:none}") // Thêm dấu :none để nếu thiếu key nó sẽ lấy chữ 'none'
    private String apiKey;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ChatService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public String chat(String userMessage, String userEmail) {
        // Build context from database
        String context = buildContext(userEmail);

        // Create system prompt with context
        String systemPrompt = """
                Bạn là chatbot hỗ trợ khách hàng cho ứng dụng đặt vé xe BookCar.
                Bạn trả lời câu hỏi về:
                - Sản phẩm/vé xe có sẵn
                - Đơn hàng và trạng thái đơn hàng
                - Quy định và chính sách
                - Thông tin chung về dịch vụ
                
                Thông tin hiện có:
                """ + context + """
                
                Hãy trả lời một cách thân thiện, chuyên nghiệp và hữu ích.
                Nếu không biết thông tin, hãy nói rằng bạn không thể tìm thấy thông tin đó hoặc yêu cầu liên hệ hỗ trợ.
                """;

        // Call OpenAI Chat Completions API directly (aligns with the Python notebook usage)
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<Object> messages = new ArrayList<>();
        messages.add(new java.util.LinkedHashMap<String, Object>() {{
            put("role", "system");
            put("content", systemPrompt);
        }});
        messages.add(new java.util.LinkedHashMap<String, Object>() {{
            put("role", "user");
            put("content", userMessage);
        }});

        java.util.Map<String, Object> payload = new java.util.LinkedHashMap<>();
        payload.put("model", "gpt-4o-mini");
        payload.put("messages", messages);
        payload.put("max_tokens", 500);
        payload.put("temperature", 0.7);

        HttpEntity<java.util.Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<java.util.Map> response = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    java.util.Map.class
            );

            java.util.Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("choices")) {
                return "Xin lỗi, không nhận được phản hồi từ OpenAI.";
            }

            java.util.List<?> choices = (java.util.List<?>) body.get("choices");
            if (choices.isEmpty()) {
                return "Xin lỗi, không có phản hồi hợp lệ từ OpenAI.";
            }

            Object firstChoice = choices.get(0);
            if (firstChoice instanceof java.util.Map<?, ?> choiceMap) {
                Object messageObj = choiceMap.get("message");
                if (messageObj instanceof java.util.Map<?, ?> messageMap) {
                    Object content = messageMap.get("content");
                    if (content != null) {
                        return content.toString();
                    }
                }
            }
            return "Xin lỗi, không trích xuất được nội dung trả lời.";
        } catch (Exception e) {
            return "Xin lỗi, có lỗi khi gọi OpenAI: " + e.getMessage();
        }
    }

    private String buildContext(String userEmail) {
        StringBuilder context = new StringBuilder();

        // Add available products
        context.append("=== SẢN PHẨM/VÉ XE KHẢ DỤNG ===\n");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            context.append("Hiện không có sản phẩm nào\n");
        } else {
                context.append(products.stream()
                    .limit(5) // Giới hạn 5 sản phẩm để không quá dài
                    .map(p -> String.format("- %s: %s đến %s, Giá: %s VND, %d chỗ",
                        p.getName(),
                        p.getStartLocation() != null ? p.getStartLocation().getName() : "N/A",
                        p.getEndLocation() != null ? p.getEndLocation().getName() : "N/A",
                        p.getPrice(),
                        p.getQuantity_seat()))
                    .collect(Collectors.joining("\n")));
            context.append("\n");
        }

        // Add user's orders if email provided
        if (userEmail != null && !userEmail.isEmpty()) {
            context.append("\n=== ĐƠN HÀNG CỦA BẠN ===\n");
            List<Order> userOrders = orderRepository.findAll();  // Trong thực tế, cần query by email
            if (userOrders.isEmpty()) {
                context.append("Bạn chưa có đơn hàng nào\n");
            } else {
                context.append(userOrders.stream()
                    .limit(3)
                    .map(o -> String.format("- Đơn hàng #%s: %s VND, Trạng thái: %s",
                        o.getOrderID(),
                        o.getTotalPrice(),
                        o.getOrderStatus()))
                    .collect(Collectors.joining("\n")));
                context.append("\n");
            }
        }

        // Add basic FAQ
        context.append("\n=== CÂU HỎI THƯỜNG GẶP ===\n");
        context.append("- Cách đặt vé: Chọn lộ trình, ngày giờ, số lượng vé và thanh toán\n");
        context.append("- Hủy đơn: Có thể hủy trước 24 giờ khởi hành\n");
        context.append("- Liên hệ: Email support@bookcar.com hoặc gọi hotline\n");

        return context.toString();
    }
}
