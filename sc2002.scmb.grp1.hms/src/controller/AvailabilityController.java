package controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import util.DateTimeUtil;
import entity.Appointment;
import entity.Availability;
import entity.Doctor;
import repository.AvailabilityRepository;
import repository.DoctorRepository;

public class AvailabilityController {
	private final static AvailabilityRepository availabilityRepository =  new AvailabilityRepository();
	private final static DoctorRepository doctorRepository =  new DoctorRepository();
	DateTimeUtil datetimeutil =  new DateTimeUtil();
	
	public void loadAvailabilityByDoctor(String doctorId) throws IOException {
	    List<Availability> availabilityList = availabilityRepository.getAvailabilityByDoctorId(doctorId);

	    if (availabilityList.isEmpty()) {
	        System.out.println();
	        System.out.println("No availability slots found for Doctor ID: " + doctorId);
	        System.out.println();
	    } else {
	        System.out.println();
	        System.out.println("Availability for Doctor ID: " + doctorId);
	        System.out.println("Availability ID | Date       | Start Time | End Time");

	        // Format each row to align the columns
	        for (Availability availability : availabilityList) {
	            System.out.println(String.format("%-15s | %-10s | %-10s | %-10s",
	                    availability.getAvailabilityId(),
	                    availability.getDate(),
	                    availability.getStartTime(),
	                    availability.getEndTime()));
	        }
	        System.out.println();
	    }
	}
	
	//generate new availabilityid
	public String generateNextAvailId() throws IOException {
	    String lastRecordId = availabilityRepository.getLastAvailId();
	    String numberPart = lastRecordId.substring(2);
	    int nextNumber = Integer.parseInt(numberPart) + 1;
	    return "AV" + String.format("%03d", nextNumber);
	}
	
	
	
	// Method to handle the creation of new availability
    public void createNewAvailability(String DoctorId) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Gather necessary details from user
        String doctorId = DoctorId;
        String availabilityId = generateNextAvailId();

        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = scanner.nextLine();

        System.out.print("Enter Start Time (HH:MM): ");
        String startTime = scanner.nextLine();

        System.out.print("Enter End Time (HH:MM): ");
        String endTime = scanner.nextLine();

        // Validate the inputs (e.g., valid date and time format)
        if (!DateTimeUtil.isValidDate(date)) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }
        
        if (!DateTimeUtil.isValidTime(startTime)) {
            System.out.println("Invalid start time format. Please use HH:MM.");
            return;
        }
        
        if (!DateTimeUtil.isValidTime(endTime)) {
            System.out.println("Invalid end time format. Please use HH:MM.");
            return;
        }

        // Create a new Availability object
        Availability newAvailability = new Availability(availabilityId, doctorId, date, startTime, endTime);

        // Save the availability to the CSV file
        availabilityRepository.createNewAvailability(newAvailability);

        System.out.println("New availability added successfully.");
    }
    
    // create new availability from reschedule appointment
    public void createNewRescheduleAvailability(Appointment appointment) throws IOException {

        // Gather necessary details from user
        String doctorId = appointment.getDoctorId();
        String availabilityId = generateNextAvailId();

        String date = appointment.getAppointmentDate();

        String startTime = appointment.getStartTime();

        String endTime = appointment.getEndTime();

        // Create a new Availability object
        Availability newAvailability = new Availability(availabilityId, doctorId, date, startTime, endTime);

        // Save the availability to the CSV file
        availabilityRepository.createNewAvailability(newAvailability);
    }
    
    
    
    public void viewAvailableAppointmentSlotsForPatient() throws IOException {
        List<Availability> availabilityList = availabilityRepository.loadAllAvailabilities();

        if (availabilityList.isEmpty()) {
            System.out.println("No available appointment slots");
        } else {

            System.out.println("\nAvailable Appointment");
            System.out.println("Availability ID | DoctorName      | Date       | Start Time | End Time");

            // Format and display the availability list
            for (Availability availability : availabilityList) {
            	Doctor tempdoc = doctorRepository.findDoctorById(availability.getDoctorId());
                System.out.println(String.format("%-15s | %-15s | %-10s | %-10s | %-10s",
                        availability.getAvailabilityId(),
                        tempdoc.getName(),
                        availability.getDate(),
                        availability.getStartTime(),
                        availability.getEndTime()));
            }
        }
    }
    
    public boolean isAvailabilityIdExist(String availabilityId) throws IOException {
        List<Availability> allAvailabilities = availabilityRepository.loadAllAvailabilities();  // You would load all availability records here
        for (Availability availability : allAvailabilities) {
            if (availability.getAvailabilityId().equals(availabilityId)) {
                return true; // Return true if the ID matches
            }
        }
        return false; // Return false if no match is found
    }


}
