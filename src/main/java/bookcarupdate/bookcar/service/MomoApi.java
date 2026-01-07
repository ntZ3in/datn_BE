package bookcarupdate.bookcar.service;

import bookcarupdate.bookcar.models.CreateMomoRequest;
import bookcarupdate.bookcar.models.CreateMomoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momoApi", url = "https://${momo.end-point}")
public interface MomoApi {

    @PostMapping("") // DÒNG NÀY LÀ QUAN TRỌNG NHẤT ĐỂ HẾT LỖI LOG TRÊN
    CreateMomoResponse createMomoQR(@RequestBody CreateMomoRequest request);

}