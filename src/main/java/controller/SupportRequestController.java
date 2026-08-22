package controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.SupportRequest;
import service.SupportRequestService;

@RestController
@RequestMapping("/api/support")
public class SupportRequestController {

    private final SupportRequestService service;

    public SupportRequestController(SupportRequestService service) {
        this.service = service;
    }

    @PostMapping
    public SupportRequest createRequest(
            @RequestBody SupportRequest request) {

        return service.processRequest(request);
    }

    @GetMapping
    public List<SupportRequest> getAllRequests() {

        return service.getAllRequests();
    }
}