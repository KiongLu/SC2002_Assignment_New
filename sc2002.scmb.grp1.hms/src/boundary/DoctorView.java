package boundary;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import controller.*;
import entity.Appointment;
import entity.MedicationInventory;
import entity.User;

public class DoctorView {
	private final Scanner scanner = new Scanner(System.in);
	private final MedicalRecordController medicalrecordcontroller = new MedicalRecordController();
	private final AvailabilityController availabilitycontroller = new AvailabilityController();
	private final AppointmentController appointmentcontroller = new AppointmentController();
	private final AppointmentOutcomeController outcomecontroller = new AppointmentOutcomeController();
	private final MedicationInventoryController medicationinventorycontroller = new MedicationInventoryController();
	public void doctorMenu(User user) {
        while (true) {
        	System.out.println();
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 8) {
                System.out.println("Logging out...");
                break;
            }
            else if(choice == 1) {
            	try {
					medicalrecordcontroller.loadMedicalRecordsForDoctor(user.getUserId());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            
            else if (choice == 2) {
            	handleMedicalRecordOptions(user);
            }
            
            else if (choice == 3) {
            	try {
					availabilitycontroller.loadAvailabilityByDoctor(user.getUserId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            else if (choice == 4) {
            	try {
					availabilitycontroller.createNewAvailability(user.getUserId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            else if (choice == 5) {
            	handleAppointmnetRequestOptions(user);
            }
            
            else if (choice == 6) {
            	try {
					appointmentcontroller.listConfirmedAppointments(user.getUserId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

			else if (choice == 7) {
				try {
					appointmentcontroller.listConfirmedAppointments(user.getUserId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createAppointmentOutcomeForValidAppointment();
            }
            // Add functionality for each menu option here
        }
    }
	
	
	private void handleMedicalRecordOptions(User user) {
        while (true) {
            System.out.println();
            System.out.println("Medical Record Options:");
            System.out.println("1. Create New Medical Record");
            System.out.println("2. Update Existing Medical Record");
            System.out.println("3. Back to Doctor Menu");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                try {
                    medicalrecordcontroller.createMedicalRecord(user.getUserId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                try {
                	medicalrecordcontroller.updateMedicalRecord(user.getUserId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 3) {
                break; // Go back to the main doctor menu
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
	
	private void handleAppointmnetRequestOptions(User user) {
	    while (true) {
	        try {
	            appointmentcontroller.listPendingAppointments(user.getUserId());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println();
	        System.out.println("Appointment Request Options:");
	        System.out.println("1. Accept Appointment Request");
	        System.out.println("2. Decline Appointment Request");
	        System.out.println("3. Back to Doctor Menu");
	        System.out.println();

	        int choice = scanner.nextInt();
	        scanner.nextLine(); // Consume newline

	        if (choice == 1 || choice == 2) {
	            String status = (choice == 1) ? "Confirmed" : "Cancelled";
	            
	            while (true) {
	                System.out.print("Enter the Appointment ID: ");
	                String apptId = scanner.nextLine();

	                try {
	                    if (appointmentcontroller.isValidAppointmentId(apptId, user.getUserId())) {
	                        appointmentcontroller.updateAppointmentStatus(apptId, status);
	                        System.out.println("Appointment status updated to " + status);
	                        break;
	                    } else {
	                        System.out.println("Invalid Appointment ID. Please try again.");
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    break;
	                }
	            }
	        } else if (choice == 3) {
	            break; // Go back to the main doctor menu
	        } else {
	            System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}

	public void createAppointmentOutcomeForValidAppointment() {
		boolean valid = false;
		String apptID = "";
		String medicationName = "";
		Scanner scanner = new Scanner(System.in);

		while (!valid) {
			System.out.print("Please enter the Appointment ID for the Appointment we wish to create an outcome: ");
			apptID = scanner.nextLine();

			try {
				boolean isValidAndConfirmed = appointmentcontroller.isAppointmentIdValidAndConfirmed(apptID);

				if (isValidAndConfirmed) {
					// Create the appointment outcome
					outcomecontroller.createAppointmentOutcome(apptID);
					System.out.println("Appointment outcome created successfully for Appointment ID: " + apptID);
					valid = true;
				} else {
					System.out.println("The Appointment ID is not valid.");
					System.out.print("Would you like to re-enter the Appointment ID? (y/n): ");
					String userChoice = scanner.nextLine();

					if (userChoice.equalsIgnoreCase("n")) {
						System.out.println("Operation canceled. Returning to the previous menu.");
						valid = true;
					} else if (!userChoice.equalsIgnoreCase("y")) {
						System.out.println("Invalid choice. Please enter 'y' to retry or 'n' to go back.");
					}
				}
			} catch (IOException e) {
				System.out.println("Error while processing the Appointment Outcome: " + e.getMessage());
				valid = true;
			}
		}
	}




//	private void handleAppointmnetRequestOptions(User user) {
//        while (true) {
//        	
//        	try {
//				appointmentcontroller.listPendingAppointments(user.getUserId());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        	
//            System.out.println();
//            System.out.println("Appointment Request Options:");
//            System.out.println("1. Accept Appointment Request");
//            System.out.println("2. Decline Appointment Request");
//            System.out.println("3. Back to Doctor Menu");
//            System.out.println();
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline
//
//            if (choice == 1) {
//                try {
//                	System.out.println("Enter the Appointent ID: ");
//                	String ApptID = scanner.nextLine();
//                	
//                    appointmentcontroller.updateAppointmentStatus(ApptID, "Comfirmed");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (choice == 2) {
//            	try {
//                	System.out.println("Enter the Appointent ID: ");
//                	String ApptID = scanner.nextLine();
//                	
//                    appointmentcontroller.updateAppointmentStatus(ApptID, "Cancelled");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                
//            } else if (choice == 3) {
//                break; // Go back to the main doctor menu
//            } else {
//                System.out.println("Invalid choice. Please try again.");
//            }
//        }
//    }

}
