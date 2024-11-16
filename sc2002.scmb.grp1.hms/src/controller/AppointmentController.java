package controller;

import entity.Appointment;
import entity.Availability;
import entity.Doctor;
import entity.Patient;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import repository.AppointmentRepository;
import repository.AvailabilityRepository;
import repository.DoctorRepository;
import repository.PatientRepository;

public class AppointmentController {
    private AppointmentRepository appointmentRepository = new AppointmentRepository();
    private AvailabilityRepository availabilityRepository = new AvailabilityRepository();
    private PatientRepository patientrepository = new PatientRepository();
    private DoctorRepository doctorrepository = new DoctorRepository();
    private AvailabilityController availabilitycontroller = new AvailabilityController();

    public void createAppointment(String PatientID) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter the Availability ID for the slot you wish to select ('0' to exit): ");
            String AvailID = scanner.nextLine();

            if (AvailID.equals("0")) { // for user to exit back to menu
                System.out.println("Exiting appointment scheduling...");
                return;
            }

            // sanity check
            if (AvailID.isEmpty()) {
                continue;
            }

            Availability availslot = availabilityRepository.getAvailabilityById(AvailID);

            String ApptId = generateNextApptId();

            String patientId = PatientID;

            String doctorId = availslot.getDoctorId();

            String date = availslot.getDate();

            String starttime = availslot.getStartTime();

            String endtime = availslot.getEndTime();

            String Status = "Pending";

            Appointment newAppointment = new Appointment(ApptId, patientId, doctorId, date, starttime, endtime, Status);

            appointmentRepository.createNewAppointment(newAppointment);

            availabilityRepository.deleteAvailabilityById(AvailID);

            System.out.println("Appointment Pending Approver.");

            break;
        }
    }

    // generate new appointmentid
    public String generateNextApptId() throws IOException {
        String lastApptId = appointmentRepository.getLastApptId();
        if (lastApptId == "AP000") {
            return "AP000";
        }
        String numberPart = lastApptId.substring(2);
        int nextNumber = Integer.parseInt(numberPart) + 1;
        return "AP" + String.format("%03d", nextNumber);
    }
    
    public boolean listPendingAppointments(String doctorId) throws IOException {
        List<Appointment> pendingAppointments = appointmentRepository.getPendingAppointmentsByDoctorId(doctorId);
    
        if (pendingAppointments.isEmpty()) {
            System.out.println("+---------------------------------------------------+");
            System.out.println("|               Pending Appointments                |");
            System.out.println("+---------------------------------------------------+");
            System.out.println("| No pending appointments found for Doctor ID: " + doctorId + " |");
            System.out.println("+---------------------------------------------------+");
            return false; // No pending appointments
        }
    
        // Header with Doctor ID
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                     Pending Appointments                                                   |");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-12s: %-50s |\n", "Doctor ID", doctorId);
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
    
        // Table Headers
        System.out.printf("| %-15s | %-20s | %-12s | %-12s | %-10s | %-10s |\n",
                          "Appointment ID", "Patient Name", "Date", "Start Time", "End Time", "Status");
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
    
        // Table Rows
        for (Appointment appointment : pendingAppointments) {
            Patient temp = patientrepository.findPatientById(appointment.getPatientId());
            System.out.printf("| %-15s | %-20s | %-12s | %-12s | %-10s | %-10s |\n",
                              appointment.getAppointmentId(),
                              temp.getName(),
                              appointment.getAppointmentDate(),
                              appointment.getStartTime(),
                              appointment.getEndTime(),
                              appointment.getStatus());
        }
    
        System.out.println("+-------------------------------------------------------------------------------------------------------------+");
        return true; // Pending appointments exist
    }
    
    


    // Method to update the status of an appointment
    public void updateAppointmentStatus(String Appt, String newstatus) throws IOException {
        Appointment appointment = appointmentRepository.getAppointmentById(Appt);
        appointment.setStatus(newstatus);
        appointmentRepository.updateAppointment(appointment);
    }

    // check if the appointment is valid
    public boolean isValidAppointmentId(String appointmentId, String doctorId) throws IOException {
        List<Appointment> pendingAppointments = appointmentRepository.getPendingAppointmentsByDoctorId(doctorId);
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return true;
            }
        }
        return false;
    }

    // check if the appointment is valid
    public boolean isValidRescheduleAppointmentId(String appointmentId, String patientId) throws IOException {
        List<Appointment> pendingAppointments = appointmentRepository
                .getConfirmedOrPendingAppointmentsByDoctorId(patientId);
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return true;
            }
        }
        return false;
    }

    public boolean listConfirmedAppointments(String doctorId) throws IOException {
        List<Appointment> confirmedAppointments = appointmentRepository.getConfirmedAppointmentsByDoctorId(doctorId);
    
        if (confirmedAppointments.isEmpty()) {
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                        Confirmed Appointments                                             |");
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            System.out.printf("| Doctor ID: %-100s |\n", doctorId);
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            System.out.println("| No confirmed appointments found.                                                                          |");
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            return false; // Indicate no confirmed appointments
        }
    
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                                       Confirmed Appointments                                                           |");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| Doctor ID: %-122s |\n", doctorId);
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| Appointment ID | Patient Name          | Gender   | Age | Phone Number  | Email                | Blood Type | Date       | Time Slot   |");
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------+");
    
        for (Appointment appointment : confirmedAppointments) {
            Patient temp = patientrepository.findPatientById(appointment.getPatientId());
            System.out.printf("| %-14s | %-20s | %-8s | %-3s | %-13s | %-20s | %-10s | %-10s | %-10s |\n",
                              appointment.getAppointmentId(),
                              temp.getName(),
                              temp.getGender(),
                              temp.getAge(),
                              temp.getPhoneNumber(),
                              temp.getEmail(),
                              temp.getBloodtype(),
                              appointment.getAppointmentDate(),
                              appointment.getStartTime() + " - " + appointment.getEndTime());
        }
    
        System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------+");
        return true; // Indicate that confirmed appointments were found
    }
    


    public void listofScheduledAppointments(String patientId) throws IOException {
        List<Appointment> scheduleAppointments = appointmentRepository
                .getConfirmedOrPendingAppointmentsByDoctorId(patientId);

        if (scheduleAppointments.isEmpty()) {
            System.out.println("No Schediuled Appointments");
        } else {
            System.out.println("+---------------------------------------------------------------------+");
            System.out.println("|                        Scheduled Appointments                       |");
            System.out.println("+---------------------------------------------------------------------+");
            System.out.printf("| %-15s | %-12s | %-10s | %-10s | %-8s |\n",
                    "Appointment ID", "Doctor Name", "Date", "Start Time", "End Time");
            System.out.println("+---------------------------------------------------------------------+");
            for (Appointment appointment : scheduleAppointments) {
                Doctor temp = doctorrepository.findDoctorById(appointment.getDoctorId());
                System.out.printf("| %-15s | %-12s | %-10s | %-10s | %-8s |\n",
                    appointment.getAppointmentId(),
                    temp.getName(),
                    appointment.getAppointmentDate(),
                    appointment.getStartTime(),
                    appointment.getEndTime());   
            }
            System.out.println("+---------------------------------------------------------------------+");            
        }
    }

    // reshedule appointment
    public void ScheduleAppointment(String oldAppointmentID, String availID, String patientId) throws IOException {
        Appointment oldappt = appointmentRepository.getAppointmentById(oldAppointmentID);
        availabilitycontroller.createNewRescheduleAvailability(oldappt);
        appointmentRepository.removeAppointmentById(oldappt.getAppointmentId());
        createRescheduleAppointment(patientId, availID);

    }

    // cancel appointment
    public void CancelAppointment(String oldAppointmentID) throws IOException {
        Appointment oldappt = appointmentRepository.getAppointmentById(oldAppointmentID);
        availabilitycontroller.createNewRescheduleAvailability(oldappt);
        appointmentRepository.removeAppointmentById(oldappt.getAppointmentId());

    }

    // create new appointment base on user new choice
    public void createRescheduleAppointment(String PatientID, String AvailID) throws IOException {

        Availability availslot = availabilityRepository.getAvailabilityById(AvailID);

        String ApptId = generateNextApptId();

        String patientId = PatientID;

        String doctorId = availslot.getDoctorId();

        String date = availslot.getDate();

        String starttime = availslot.getStartTime();

        String endtime = availslot.getEndTime();

        String Status = "Pending";

        Appointment newAppointment = new Appointment(ApptId, patientId, doctorId, date, starttime, endtime, Status);

        appointmentRepository.createNewAppointment(newAppointment);

        availabilityRepository.deleteAvailabilityById(AvailID);

        System.out.println("Appointment Pending Approval.");
    }

    public boolean isAppointmentIdValidAndConfirmed(String appointmentId) throws IOException {
        List<Appointment> allAppointments = appointmentRepository.loadAllAppointments();

        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentId().equals(appointmentId) && appointment.getStatus().equals("Confirmed")) {
                return true;
            }
        }

        // Return false if no matching appointment is found or status is not confirmed
        return false;
    }
}
