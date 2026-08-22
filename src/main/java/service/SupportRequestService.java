package service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.AiResponseService;
import ai.DateExtractor;
import ai.IntentService;
import ai.ServiceTypeExtractor;
import ai.VehicleNumberExtractor;

import model.Appointment;
import model.ServiceRecord;
import model.SupportRequest;
import model.Vehicle;

import repository.SupportRequestRepository;

@Service
public class SupportRequestService {

    private final SupportRequestRepository repository;

    private final IntentService intentService;

    private final AiResponseService aiResponseService;

    private final VehicleNumberExtractor vehicleNumberExtractor;

    private final VehicleService vehicleService;

    private final ServiceRecordService serviceRecordService;

    private final ServiceTypeExtractor serviceTypeExtractor;

    private final AppointmentService appointmentService;

    private final DateExtractor dateExtractor;

    public SupportRequestService(

            SupportRequestRepository repository,

            IntentService intentService,

            AiResponseService aiResponseService,

            VehicleNumberExtractor vehicleNumberExtractor,

            VehicleService vehicleService,

            ServiceRecordService serviceRecordService,

            ServiceTypeExtractor serviceTypeExtractor,

            AppointmentService appointmentService,

            DateExtractor dateExtractor) {

        this.repository = repository;

        this.intentService = intentService;

        this.aiResponseService = aiResponseService;

        this.vehicleNumberExtractor = vehicleNumberExtractor;

        this.vehicleService = vehicleService;

        this.serviceRecordService = serviceRecordService;

        this.serviceTypeExtractor = serviceTypeExtractor;

        this.appointmentService = appointmentService;

        this.dateExtractor = dateExtractor;
    }

    public SupportRequest processRequest(
            SupportRequest request) {

        // =====================================================
        // 1. Read customer message
        // =====================================================

        String message = request.getMessage();


        // =====================================================
        // 2. Detect customer intent
        // =====================================================

        String intent =
                intentService.detectIntent(message);


        // =====================================================
        // 3. Extract vehicle registration number
        // =====================================================

        String vehicleNumber =
                vehicleNumberExtractor
                        .extractVehicleNumber(message);


        // =====================================================
        // 4. Find vehicle
        // =====================================================

        Vehicle vehicle = null;

        if (vehicleNumber != null) {

            vehicle =
                    vehicleService
                            .getVehicleByRegistrationNumber(
                                    vehicleNumber);
        }


        // =====================================================
        // 5. Get latest service record
        // =====================================================

        ServiceRecord latestService = null;

        if (("VEHICLE_STATUS".equals(intent)
                || "SERVICE_STATUS".equals(intent)
                || "SERVICE_CANCEL".equals(intent)
                || "SERVICE_RESCHEDULE".equals(intent))
                && vehicleNumber != null) {

            latestService =
                    serviceRecordService
                            .getLatestServiceByVehicle(
                                    vehicleNumber);

            if (latestService != null) {

                System.out.println(
                        "Latest Service ID: "
                                + latestService.getId());

                System.out.println(
                        "Latest Service Date: "
                                + latestService.getServiceDate());

                System.out.println(
                        "Latest Service Status: "
                                + latestService.getStatus());
            }
        }


        // =====================================================
        // 6. Get latest appointment
        // =====================================================

        Appointment latestAppointment = null;

        if (("VEHICLE_STATUS".equals(intent)
                || "APPOINTMENT_STATUS".equals(intent))
                && vehicleNumber != null) {

            latestAppointment =
                    appointmentService
                            .getLatestAppointmentByVehicle(
                                    vehicleNumber);

            if (latestAppointment != null) {

                System.out.println(
                        "Latest Appointment Status: "
                                + latestAppointment
                                        .getStatus());

                System.out.println(
                        "Latest Appointment Date: "
                                + latestAppointment
                                        .getAppointmentDate());
            }
        }


        // =====================================================
        // 7. Automatically create service record
        // =====================================================

        if ("SERVICE_REQUEST".equals(intent)
                && vehicleNumber != null) {

            // Extract service type
            String serviceType =
                    serviceTypeExtractor
                            .extractServiceType(message);

            ServiceRecord serviceRecord =
                    new ServiceRecord();

            serviceRecord.setCustomerId(
                    request.getCustomerId());

            serviceRecord.setVehicleNumber(
                    vehicleNumber);

            serviceRecord.setServiceType(
                    serviceType);

            serviceRecord.setDescription(
                    message);

            serviceRecord.setServiceDate(
                    null);

            serviceRecord.setStatus(
                    "PENDING");

            serviceRecordService
                    .createService(serviceRecord);
        }


        // =====================================================
        // 8. Automatically create appointment
        // =====================================================

        if ("APPOINTMENT".equals(intent)
                && vehicleNumber != null) {

            // Extract appointment date
            String extractedDate =
                    dateExtractor.extractDate(message);

            Appointment appointment =
                    new Appointment();

            appointment.setCustomerId(
                    request.getCustomerId());

            appointment.setVehicleNumber(
                    vehicleNumber);

            // Use customer's requested date
            if (extractedDate != null) {

                appointment.setAppointmentDate(
                        extractedDate);

            } else {

                // If no date was provided,
                // use today's date
                appointment.setAppointmentDate(
                        LocalDate.now().toString());
            }

            // Default appointment status
            appointment.setStatus(
                    "BOOKED");


            // Check for duplicate appointment
            Appointment existingAppointment =
                    appointmentService
                            .getAppointmentByVehicleAndDate(
                                    vehicleNumber,
                                    appointment
                                            .getAppointmentDate());

            if (existingAppointment != null) {

                // Do not create duplicate
                latestAppointment =
                        existingAppointment;

                System.out.println(
                        "Appointment already exists: "
                                + existingAppointment
                                        .getId());

            } else {

                // Create new appointment
                latestAppointment =
                        appointmentService
                                .createAppointment(
                                        appointment);

                System.out.println(
                        "Appointment Created: "
                                + latestAppointment
                                        .getId());
            }

            System.out.println(
                    "Appointment Date: "
                            + latestAppointment
                                    .getAppointmentDate());
        }


        // =====================================================
        // 9. Cancel latest appointment
        // =====================================================

        if ("APPOINTMENT_CANCEL".equals(intent)
                && vehicleNumber != null) {

            latestAppointment =
                    appointmentService
                            .cancelLatestAppointmentByVehicle(
                                    vehicleNumber);

            if (latestAppointment != null) {

                System.out.println(
                        "Appointment Cancelled: "
                                + latestAppointment
                                        .getId());

                System.out.println(
                        "Appointment Status: "
                                + latestAppointment
                                        .getStatus());

            } else {

                System.out.println(
                        "No appointment found for vehicle: "
                                + vehicleNumber);
            }
        }


        // =====================================================
        // 10. Reschedule latest appointment
        // =====================================================

        if ("APPOINTMENT_RESCHEDULE".equals(intent)
                && vehicleNumber != null) {

            // Extract new appointment date
            String newDate =
                    dateExtractor.extractDate(message);

            if (newDate != null) {

                latestAppointment =
                        appointmentService
                                .rescheduleLatestAppointmentByVehicle(
                                        vehicleNumber,
                                        newDate);

                if (latestAppointment != null) {

                    System.out.println(
                            "Appointment Rescheduled: "
                                    + latestAppointment
                                            .getId());

                    System.out.println(
                            "New Appointment Date: "
                                    + latestAppointment
                                            .getAppointmentDate());

                    System.out.println(
                            "Appointment Status: "
                                    + latestAppointment
                                            .getStatus());

                } else {

                    System.out.println(
                            "No appointment found for vehicle: "
                                    + vehicleNumber);
                }

            } else {

                System.out.println(
                        "No new appointment date found.");
            }
        }


        // =====================================================
        // 11. Cancel latest service
        // =====================================================

        if ("SERVICE_CANCEL".equals(intent)
                && vehicleNumber != null) {

            latestService =
                    serviceRecordService
                            .cancelLatestServiceByVehicle(
                                    vehicleNumber);

            if (latestService != null) {

                System.out.println(
                        "Service Cancelled: "
                                + latestService
                                        .getId());

                System.out.println(
                        "Service Status: "
                                + latestService
                                        .getStatus());

            } else {

                System.out.println(
                        "No service found for vehicle: "
                                + vehicleNumber);
            }
        }


        // =====================================================
        // 12. Reschedule latest service
        // =====================================================

        if ("SERVICE_RESCHEDULE".equals(intent)
                && vehicleNumber != null) {

            // Extract new service date
            String newServiceDate =
                    dateExtractor.extractDate(message);

            if (newServiceDate != null) {

                latestService =
                        serviceRecordService
                                .rescheduleLatestServiceByVehicle(
                                        vehicleNumber,
                                        newServiceDate);

                if (latestService != null) {

                    System.out.println(
                            "Service Rescheduled: "
                                    + latestService
                                            .getId());

                    System.out.println(
                            "New Service Date: "
                                    + latestService
                                            .getServiceDate());

                    System.out.println(
                            "Service Status: "
                                    + latestService
                                            .getStatus());

                } else {

                    System.out.println(
                            "No service found for vehicle: "
                                    + vehicleNumber);
                }

            } else {

                System.out.println(
                        "No new service date found.");
            }
        }


        // =====================================================
        // 13. Generate AI response
        // =====================================================

        String response =
                aiResponseService.generateResponse(
                        intent,
                        message,
                        vehicle,
                        latestService,
                        latestAppointment);


        // =====================================================
        // 14. Store intent
        // =====================================================

        request.setIntent(
                intent);


        // =====================================================
        // 15. Store vehicle number
        // =====================================================

        request.setVehicleNumber(
                vehicleNumber);


        // =====================================================
        // 16. Store AI response
        // =====================================================

        request.setResponse(
                response);


        // =====================================================
        // 17. Save support request
        // =====================================================

        return repository.save(request);
    }


    // =====================================================
    // GET ALL SUPPORT REQUESTS
    // =====================================================

    public List<SupportRequest> getAllRequests() {

        return repository.findAll();
    }
}