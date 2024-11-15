package repository;

import entity.Appointment;
import entity.Availability;
import util.CSVUtil;

import java.io.*;
import java.util.*;

public class AppointmentRepository {
    private static final String FILE_PATH_APPOINTMENT = "/data/Appointment.csv";
    private static final CSVUtil csvutil = new CSVUtil(); 
    
 // Method to write a new appointment to the CSV file
    public void createNewAppointment(Appointment appointment) throws IOException {
        File file = new File(FILE_PATH_APPOINTMENT);

        // Open the file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // If the file is not empty, write a newline first
            if (file.length() > 0) {
                writer.newLine();
            }

            // Format the appointment data as CSV
            String appointmentData = String.join(",",
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getAppointmentDate(),
                    appointment.getStartTime(),
                    appointment.getEndTime(),
                    appointment.getStatus());

            // Write the new appointment data to the file
            writer.write(appointmentData);
            writer.flush();
        }
        CSVUtil.removeEmptyRows(FILE_PATH_APPOINTMENT);
    }




    
 // Method to load appointment data from the CSV file
    public List<Appointment> loadAllAppointments() throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH_APPOINTMENT));
            String line;
            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    String appointmentId = fields[0];
                    String patientId = fields[1];
                    String doctorId = fields[2];
                    String appointmentDate = fields[3];
                    String startTime = fields[4];
                    String endTime = fields[5];
                    String status = fields[6];

                    Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentDate, startTime, endTime, status);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading appointment data: " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return appointments;
    }

    // Method to get an appointment by appointmentId
    public Appointment getAppointmentById(String appointmentId) throws IOException {
        List<Appointment> allAppointments = loadAllAppointments();

        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return appointment; // Return the appointment record that matches the appointmentId
            }
        }

        return null; // If no match found, return null
    }

    
    // Get the last AppointmentId from the existing records
    public String getLastApptId() throws IOException {
        List<Appointment> appointment = loadAllAppointments();
        if (appointment.isEmpty()) {
            return "AP000";
        }
        String lastAvailableId = appointment.get(appointment.size() - 1).getAppointmentId();
        return lastAvailableId;
    }
    
    
 // Method to get all pending appointments for a specific doctor
    public List<Appointment> getPendingAppointmentsByDoctorId(String doctorId) throws IOException {
        List<Appointment> allAppointments = loadAllAppointments();
        List<Appointment> pendingAppointments = new ArrayList<>();

        for (Appointment appointment : allAppointments) {
            if (appointment.getDoctorId().equals(doctorId) && "Pending".equalsIgnoreCase(appointment.getStatus())) {
                pendingAppointments.add(appointment);
            }
        }

        return pendingAppointments;
    }





	public void updateAppointment(Appointment updatedAppointment)throws IOException {
		 List<Appointment> allAppointments = loadAllAppointments();

		    // Find the appointment by ID and update its status
		    for (Appointment appointment : allAppointments) {
		        if (appointment.getAppointmentId().equals(updatedAppointment.getAppointmentId())) {
		            appointment.setStatus(updatedAppointment.getStatus());
		            break;
		        }
		    }

		    // Rewrite the CSV file with updated appointments
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_APPOINTMENT))) {
		        writer.write("AppointmentId,PatientId,DoctorId,AppointmentDate,StartTime,EndTime,Status\n"); // CSV header
		        for (Appointment appointment : allAppointments) {
		            String appointmentData = String.join(",",
		                    appointment.getAppointmentId(),
		                    appointment.getPatientId(),
		                    appointment.getDoctorId(),
		                    appointment.getAppointmentDate(),
		                    appointment.getStartTime(),
		                    appointment.getEndTime(),
		                    appointment.getStatus());
		            writer.write(appointmentData);
		            writer.newLine();
		        }
		    }
	}
	
	public List<Appointment> getConfirmedAppointmentsByDoctorId(String doctorId) throws IOException {
	    List<Appointment> confirmedAppointments = new ArrayList<>();
	    List<Appointment> allAppointments = loadAllAppointments();

	    for (Appointment appointment : allAppointments) {
	        if (appointment.getDoctorId().equalsIgnoreCase(doctorId) && 
	            appointment.getStatus().equalsIgnoreCase("Confirmed")) {
	            confirmedAppointments.add(appointment);
	        }
	    }

	    return confirmedAppointments;
	}
	
	public List<Appointment> getConfirmedOrPendingAppointmentsByDoctorId(String patientId) throws IOException {
	    List<Appointment> filteredAppointments = new ArrayList<>();
	    List<Appointment> allAppointments = loadAllAppointments();

	    for (Appointment appointment : allAppointments) {
	        if (appointment.getPatientId().equalsIgnoreCase(patientId) && 
	            (appointment.getStatus().equalsIgnoreCase("Confirmed") || appointment.getStatus().equalsIgnoreCase("Pending"))) {
	            filteredAppointments.add(appointment);
	        }
	    }
	    return filteredAppointments;
	}
	
	
	public boolean removeAppointmentById(String appointmentId) throws IOException {
	    List<Appointment> allAppointments = loadAllAppointments();
	    boolean removed = allAppointments.removeIf(appointment -> appointment.getAppointmentId().equalsIgnoreCase(appointmentId));

	    // Rewrite the CSV file without the removed appointment
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_APPOINTMENT))) {
	        writer.write("AppointmentId,PatientId,DoctorId,AppointmentDate,StartTime,EndTime,Status"); // Write header
	        writer.newLine();
	        
	        for (Appointment appointment : allAppointments) {
	            String appointmentData = String.join(",",
	                    appointment.getAppointmentId(),
	                    appointment.getPatientId(),
	                    appointment.getDoctorId(),
	                    appointment.getAppointmentDate(),
	                    appointment.getStartTime(),
	                    appointment.getEndTime(),
	                    appointment.getStatus());
	            writer.write(appointmentData);
	            writer.newLine();
	        }
	    }

	    return removed; // Returns true if an appointment was removed, otherwise false
	}
}

