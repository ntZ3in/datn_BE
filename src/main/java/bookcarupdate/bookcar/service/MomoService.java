package bookcarupdate.bookcar.service;

import bookcarupdate.bookcar.models.CreateMomoRequest;
import bookcarupdate.bookcar.models.CreateMomoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomoService {

    @Value("${MOMO}")
    private String PARTNER_CODE;

    @Value("${F8BBA842ECF85}")
    private String ACCESS_KEY;

    @Value("${K951B6PE1waDMI640xX08PD3vg6EkVlz}")
    private String SECRET_KEY;

    @Value("${http://localhost:3000/}")
    private String REDIRECT_URL;

    @Value("${http://localhost:8080/api/momo/ipn-handler}")
    private String IPN_URL;

    @Value("${captureWallet}")
    private String REQUEST_TYPE;

    private final MomoApi momoApi;

    public CreateMomoResponse createQR() {
        String orderId = UUID.randomUUID().toString();
        String orderInfo = "Thanh toan don hang: " + orderId;
        String requestId = UUID.randomUUID().toString();
        String extraData = ""; // Nên để trống nếu không có dữ liệu thêm
        long amount = 10000;

        // 1. Sửa lại chuỗi rawSignature theo đúng thứ tự alphabet và đúng ký tự nối &
        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                ACCESS_KEY, amount, extraData, IPN_URL, orderId, orderInfo, PARTNER_CODE, REDIRECT_URL, requestId, REQUEST_TYPE
        );

        try {
            String signature = signHmacSha256(rawSignature, SECRET_KEY);

            CreateMomoRequest request = CreateMomoRequest.builder()
                    .partnerCode(PARTNER_CODE)
                    .requestType(REQUEST_TYPE)
                    .ipnUrl(IPN_URL)
                    .redirectUrl(REDIRECT_URL)
                    .orderId(orderId)
                    .orderInfo(orderInfo)
                    .requestId(requestId)
                    .amount(amount) // Đừng quên gán amount vào request
                    .extraData(extraData)
                    .signature(signature)
                    .lang("vi")
                    .build();

            return momoApi.createMomoQR(request);
        } catch (Exception e) {
            log.error("Lỗi tạo chữ ký hoặc gọi API: ", e);
            return null;
        }
    }

    // 2. Sửa lại hàm băm chuẩn xác
    private String signHmacSha256(String data, String key) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            // Sử dụng Integer.toHexString trực tiếp
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}