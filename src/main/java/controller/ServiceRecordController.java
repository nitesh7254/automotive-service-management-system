package controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.ServiceRecord;
import service.ServiceRecordService;

@RestController
@RequestMapping("/api/services")
public class ServiceRecordController {

    private final ServiceRecordService service;

    public ServiceRecordController(ServiceRecordService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ServiceRecord createService(
            @RequestBody ServiceRecord serviceRecord) {

        return service.createService(serviceRecord);
    }

    // GET ALL
    @GetMapping
    public List<ServiceRecord> getAllServices() {
        return service.getAllServices();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ServiceRecord getServiceById(
            @PathVariable Long id) {

        return service.getServiceById(id);
    }

    // GET BY CUSTOMER
    @GetMapping("/customer/{customerId}")
    public List<ServiceRecord> getByCustomer(
            @PathVariable Long customerId) {

        return service.getServicesByCustomer(customerId);
    }

    // GET BY VEHICLE
    @GetMapping("/vehicle/{vehicleNumber}")
    public List<ServiceRecord> getByVehicle(
            @PathVariable String vehicleNumber) {

        return service.getServicesByVehicle(vehicleNumber);
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public List<ServiceRecord> getByStatus(
            @PathVariable String status) {

        return service.getServicesByStatus(status);
    }

    // UPDATE SERVICE
    @PutMapping("/{id}")
    public ServiceRecord updateService(
            @PathVariable Long id,
            @RequestBody ServiceRecord serviceRecord) {

        return service.updateService(id, serviceRecord);
    }

    // UPDATE STATUS
    @PatchMapping("/{id}/status")
    public ServiceRecord updateServiceStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updateServiceStatus(id, status);
    }

    // CANCEL SERVICE
    @PatchMapping("/{id}/cancel")
    public ServiceRecord cancelService(
            @PathVariable Long id) {

        return service.cancelService(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteService(
            @PathVariable Long id) {

        service.deleteService(id);

        return "Service record deleted successfully";
    }
}