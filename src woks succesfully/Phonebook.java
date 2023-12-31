
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.*;

public class Phonebook {
	LocalDateTime localTime = LocalDateTime.now();
	Scanner input;
	LinkedList Linked;
	ContactBST bst;

	public Phonebook() {
		input = new Scanner(System.in);
		Linked = new LinkedList();
		localTime = LocalDateTime.now();
		bst = new ContactBST();

	}
	public void addContact() {
		input = new Scanner(System.in); //1

		String givenName; //1
		boolean containsAlphabets = false; //1
		do {  //n+1
			System.out.println("Enter the Contact's name: "); //n+1 
			givenName = input.nextLine();  //n+1 
			containsAlphabets = Pattern.matches(".*[a-zA-Z].*", givenName);  //n+1 
			if (!containsAlphabets) {  //n+1 
				System.out.println("Name must be a String !");  //n+1 
			}  //n+1  
			if (givenName.length() < 4) {  //n+1 
				System.out.println("Name must be 4 charcters long !");  //n+1 
			}  //n+1 
			
		} while (!containsAlphabets || givenName.length() < 4 || bst.searchByAnyTerm(givenName)!=null ); //newww

		String givenPhoneNumber = null; //1
		boolean isDigit = false; //1
		do {  //n+1 
			isDigit = true;  //n+1 
			System.out.println("Enter Phone Number: ");  //n+1 
			givenPhoneNumber = input.nextLine();  //n+1 

			if (givenPhoneNumber.length() != 10) {  //n+1 
				System.out.println("Number must be of length 10!");  //n+1 
				continue; // Continue to the next iteration of the loop.  //n+1 
			}

			for (int i = 0; i < givenPhoneNumber.length(); i++) { //n^2+2n+1
				char c = givenPhoneNumber.charAt(i); //n^2+n
				if (!Character.isDigit(c)) { //n^2+n
					isDigit = false; //n^2+n
					break; // Exit the loop if a non-digit character is found. //n^2+n
				} //n^2+n
			}
			if (!isDigit) {   //n+1 
				System.out.println("The Phone number must be a number!");  //n+1 
			}  //n+1 

			if(bst.searchByAnyTerm(givenPhoneNumber)!=null)
				System.out.println("The phone Number is already used in another contact");

		} while (givenPhoneNumber.length() != 10 || !isDigit || (bst.searchByAnyTerm(givenPhoneNumber)!=null)); //n
		String givenEmailAddress = null; //1

		do { //n+1
			System.out.println("Enter Email Address: "); //n+1
			givenEmailAddress = input.nextLine(); //n+1
			if(!givenEmailAddress.contains("@") || !givenEmailAddress.contains(".") || givenEmailAddress.length() < 7) //n+1
				System.out.println("incorrect Email Adress Format!"); //n+1
		} while (!givenEmailAddress.contains("@") || !givenEmailAddress.contains(".") || givenEmailAddress.length() < 7); //n
		System.out.println("Enter Address: "); //1
		String givenAddress = input.nextLine(); //1

		String givenBirthday = null; //1
		do { //n+1
			System.out.println("Enter Birthday:(MM/DD/YYYY)");  //n+1
			givenBirthday = input.nextLine();  //n+1
			if (givenBirthday.length() < 10 || !givenBirthday.contains("/"))  //n+1
				System.out.println("Birthday must be with the format (MM/DD/YYYY)");  //n+1
		} while (givenBirthday.length() < 10 || !givenBirthday.contains("/") || !givenBirthday.matches("\\d{2}/\\d{2}/\\d{4}")); //n

		System.out.println("Enter notes: ");  //1
		String givenNotes = input.nextLine(); //1
		Contact contact = new Contact(givenName, givenPhoneNumber, givenEmailAddress, givenAddress, givenBirthday,
				givenNotes); //1
		if (bst.insertContact(contact)) //1
			System.out.println("Contact added successfully!"); //1
		System.out.println(); //1

	}

	public void searchContact() {  //1
		input = new Scanner(System.in); //1 
		String searchedCriteria = null; //1
		int choice = 0; //1
		do { //n+1
			System.out.println("Enter search criteria: "); //n+1
			System.out.println("1. Name"); //n+1
			System.out.println("2. Phone Number"); //n+1
			System.out.println("3. Email Address"); //n+1
			System.out.println("4. Address"); //n+1
			System.out.println("5. Birthday"); //n+1
			try { //n+1
				choice = input.nextInt(); //n+1
			} catch (Exception erorr) { //n+1
				System.out.println("Sorry but you have to choose between these criterias !"); //n+1
				input.nextLine(); //n+1
			} //n+1
		} while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5); //n

		switch (choice) { //1
		case 1: //1
			System.out.println("Enter the Contact's name:"); //1
			input.nextLine();
			searchedCriteria = input.nextLine(); //1
			break; //1
		case 2: //1
			System.out.println("Enter Phone Number: "); //1
			searchedCriteria = input.next(); //1
			break; //1
		case 3: //1
			System.out.println("Enter Email Address: "); //1
			searchedCriteria = input.next(); //1
			break; //1
		case 4: //1
			System.out.println("Enter Address: "); //1
			input.nextLine();
			searchedCriteria = input.nextLine(); //1
			break; //1
		case 5: //1
			System.out.println("Enter Birthday:"); //1
			searchedCriteria = input.next(); //1
			break; //1
		} //1
		Contact searchedContact = bst.searchByAnyTerm(searchedCriteria); //1
		if (searchedContact != null) { //1
			searchedContact.printContactInfo(); //1
		} else { //1
			System.out.println("Unfound Contact !"); //1
		} //1
		input.nextLine(); //1
	} //1

	private static int[] splitDateTime(String dateTimeStr) { 
		String[] parts = dateTimeStr.split(" "); //1
		String[] dateParts = parts[0].split("/"); //1
		String[] timeParts = parts[1].split(":"); //1

		int year = Integer.parseInt(dateParts[2]); //1
		int month = Integer.parseInt(dateParts[0]); //1
		int day = Integer.parseInt(dateParts[1]); //1
		int hour = Integer.parseInt(timeParts[0]); //1
		int minutes = Integer.parseInt(timeParts[1]); //1

		return new int[] { year, month, day, hour, minutes }; //1
	}

	public void deleteContact() {
		input = new Scanner(System.in); //1
		System.out.println("Enter the contact's name that you want to delete: ");//1
		String deletingName = input.nextLine();//1
		
	    Contact contactToDelete = bst.searchByAnyTerm(deletingName);

		if (bst.removeContact(contactToDelete)) { //n^2
			System.out.println("Contact has been deleted"); //1
		} else { //1
			System.out.println("Contact was not found"); //1
		} //1
	}

	public void scheduleAppointment() {
		Scanner input = new Scanner(System.in); //1
		System.out.println("Enter appointment title: "); //1
		String eventTitle = input.nextLine(); //1

		System.out.println("Enter contact name: "); //1
		String contactName = input.nextLine(); //1
	
		Contact scheduledContact = bst.searchByAnyTerm(contactName); //n
		if (scheduledContact == null) { //1
			System.out.println("Contact was not found"); //1
			return; //1
		} //1

		if(!scheduledContact.HasEvent()) { //1
			System.out.println("Contact already has an appointment");
			return;
		}

		int resultDateTime[]; //1
		int year = 0; //1
		int month = 0; //1
		int dayOfMonth = 0; //1
		int hour = 0; //1
		int minute = 0; //1
		String eventDateAndTime = null; //1
		do { //n+1
			try { //n+1
				do {  //n^2+2n+1
					System.out.println("Enter appointment date and time (MM/DD/YYYY HH:MM)"); //n^2+2n+1
					eventDateAndTime = input.nextLine();  //n^2+2n+1

					if (eventDateAndTime.length() < 16 || !eventDateAndTime.contains("/")  
							|| !eventDateAndTime.contains(":"))  //n^2+2n+1
						System.out.println("appointment date and time must be with the format (MM/DD/YY HH:MM)");  //n^2+2n+1
				} while (eventDateAndTime.length() < 16 || !eventDateAndTime.contains("/")
						|| !eventDateAndTime.contains(":")); //n^2 +n

				resultDateTime = splitDateTime(eventDateAndTime);  //n+1
				year = resultDateTime[0];  //n+1
				month = resultDateTime[1];  //n+1
				dayOfMonth = resultDateTime[2];  //n+1
				hour = resultDateTime[3];  //n+1
				minute = resultDateTime[4];  //n+1

			} catch (Exception e) {  //n+1
				System.out.println("Enter right value");  //n+1
				input.nextLine();  //n+1
			}  //n+1

		} while (month > 12 || dayOfMonth > 31 || hour > 24 || minute > 60 || eventDateAndTime.length() < 16
				|| LocalDateTime.of(year, month, dayOfMonth, hour, minute).isBefore(localTime)); //n

		System.out.println("Enter appointment location: "); //1
		String eventLocation = input.nextLine(); //1

		Event event = new Event(eventTitle, contactName, year, month, dayOfMonth, hour, minute, eventLocation); //1
		
		if (Linked.addEvent(event)) { //1
	        scheduledContact.addEvent(event); //1
	        System.out.println("Appointment has been scheduled!"); //1	    
		} //1
		else //1
	        System.out.println("There might be a conflict with another event/appointment at the same time"); //1
	} //1
	
	public void scheduleEvent() {
	    Scanner input = new Scanner(System.in);

	    System.out.println("Enter event title: ");
	    String eventTitle = input.nextLine();

	    Contact[] contacts = new Contact[10];  // Assuming no event can have more than 10 contacts
	    int contactsCount = 0;

	    boolean addMoreContacts;
	    
	    do {
	        System.out.println("Enter contact name: ");
	        String contactName = input.nextLine();

	        Contact scheduledContact = bst.searchByAnyTerm(contactName);
	        if (scheduledContact == null) {
	            System.out.println("Contact was not found");
	        } else if (scheduledContact.HasEvent()) {
	            contacts[contactsCount++] = scheduledContact;

	            if (contactsCount >= 10) {
	                System.out.println("Maximum number of contacts reached (10).");
	                break;
	            }
	        } else {
	            System.out.println("Contact already has an event");
	        }

	        System.out.println("Do you want to add more contacts? (1. Yes / 2. No)");
	        int choice = input.nextInt();
	        addMoreContacts = (choice == 1);
	        input.nextLine();  // Consume the newline character
	    } while (addMoreContacts);

	    int resultDateTime[];
	    int year=0, month=0, dayOfMonth = 0, hour=0, minute=0;
	    String eventDateAndTime="";

	    do {
	        try {
	            do {
	                System.out.println("Enter event date and time (MM/DD/YYYY HH:MM)");
	                eventDateAndTime = input.nextLine();

	                if (eventDateAndTime.length() < 16 || !eventDateAndTime.contains("/") || !eventDateAndTime.contains(":")) {
	                    System.out.println("Event date and time must be in the format (MM/DD/YYYY HH:MM)");
	                }
	            } while (eventDateAndTime.length() < 16 || !eventDateAndTime.contains("/") || !eventDateAndTime.contains(":"));

	            resultDateTime = splitDateTime(eventDateAndTime);
	            year = resultDateTime[0];
	            month = resultDateTime[1];
	            dayOfMonth = resultDateTime[2];
	            hour = resultDateTime[3];
	            minute = resultDateTime[4];

	        } catch (Exception e) {
	            System.out.println("Enter the right value");
	            input.nextLine();
	            continue;
	        }

	    } while (month > 12 || dayOfMonth > 31 || hour > 24 || minute > 60
	            || eventDateAndTime.length() < 16 || LocalDateTime.of(year, month, dayOfMonth, hour, minute).isBefore(localTime));

	    System.out.println("Enter event location: ");
	    String eventLocation = input.nextLine();

	    Event event = new Event(eventTitle, year, month, dayOfMonth, hour, minute, eventLocation, contacts);

	    if (Linked.addEvent(event)) {
	        for (Contact contact : contacts) {
	            if (contact != null) {
	                contact.addEvent(event);
	            }
	        }
	        System.out.println("Event has been scheduled!");
	    } else {
	        System.out.println("There might be a conflict with another event at the same time");
	    }
	}

	public void printEventDetails() { 
		input = new Scanner(System.in); //1
		int choice = 0; //1

		do { //n+1
			System.out.println("Enter search criteria: ");  //n+1
			System.out.println("1. Contact name: ");  //n+1
			System.out.println("2. Event title: "); //n+1

			try {  //n+1
				choice = input.nextInt();  //n+1

			} catch (Exception e) {  //n+1
				System.out.println("Enter the Right choice please !");  //n+1
				input.nextLine();  //n+1
			}  //n+1

		} while (choice != 1 && choice != 2); //n

		switch (choice) { //1
		case 1: //1
			System.out.println("Enter the contact's name: "); //1
			input.nextLine();
			String givenContactName = input.nextLine(); //1
			Contact ContactE = bst.searchByAnyTerm(givenContactName); //n
			if (ContactE != null && !ContactE.HasEvent()) { //1 
				ContactE.getEvent().printEvent(); //1
			} else { //1
				System.out.println("Event or Contact not found !"); //1
			} //1
			break;  //1
		case 2: //1
			System.out.println("Enter the event title: "); //1
			String givenTitle = input.next(); //1
			Event Eventy = Linked.searchBytitle(givenTitle); //n
			if (Eventy != null) { //1
				Eventy.printEvent(); //1
			} else { //1
				System.out.println("No Event 've been found !"); //1
			} //1
			input.nextLine(); //1
			break; //1
		} //1

	}

	public void printContactsByFirstName() { 
		System.out.println("Enter the first name:"); //1
		input = new Scanner(System.in); //1
		String firstName = input.nextLine(); //1
		bst.printContactsByFirstName(firstName); //n+1
		System.out.println("finished !"); //1
	} 

	public void printAllEventsAlphanetically() {
		Linked.PrintAllEvents(); //n+1
	}

	public void runPhoneBook() {
		input = new Scanner(System.in);
		System.out.println("Welcome to the BST Phonebook!");
		int choice = 0;
		do {

			System.out.println("Please choose an option:");
			System.out.println("1. Add a contact");
			System.out.println("2. Search for a contact");
			System.out.println("3. Delete a contact");
			System.out.println("4. Schedule an event/appointment");
			System.out.println("5. Print event details");
			System.out.println("6. Print contacts by first name");
			System.out.println("7. Print all events alphabetically");
			System.out.println("8. Exit");
			System.out.println("");
			try {
				choice = input.nextInt();
			} catch (Exception erorr) {
				System.out.println("# Enter the Right choice please # \n");
				input.nextLine();
			}
			switch (choice) {
			case 1:
				addContact();
				break;
			case 2:
				searchContact();
				break;
			case 3:
				deleteContact();
				break;
			case 4:
				System.out.println("Enter type:");
				System.out.println("1. event");
				System.out.println("2. appointment");
				int type = input.nextInt();
				switch(type) {
				case 1:
					scheduleEvent();
					break;
				case 2:
					scheduleAppointment();
					break;
				}
				break;
			case 5:
				printEventDetails();
				break;
			case 6:
				printContactsByFirstName();
				break;
			case 7:
				printAllEventsAlphanetically();
				break;
			case 8:
				System.out.println("Goodbye");

			}
		} while (choice != 8);
		input.close();
	}

	public static void main(String[] args) {
		Phonebook app = new Phonebook();
		app.runPhoneBook();
	}
}
