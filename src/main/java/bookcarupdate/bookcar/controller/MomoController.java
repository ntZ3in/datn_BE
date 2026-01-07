package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.models.CreateMomoResponse;
import bookcarupdate.bookcar.service.MomoService;
import org.springframework.web.bind.annotation.PostMapping;

public class MomoController {
    private final MomoService momoService;

    public MomoController(MomoService momoService) {
        this.momoService = momoService;
    }

    @PostMapping("create")
    public CreateMomoResponse createQR(){
        return momoService.createQR();
    }
}

