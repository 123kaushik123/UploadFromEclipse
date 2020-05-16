
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
		User user;
		UserBO userBO;
		String status;
		Boolean logout;
		Invoice invoice;

		Integer statusId;
		Integer invoiceId;
		String newLine = "\n";
		String invoiceDetails;
		List<Invoice> invoiceList;
		StringBuilder mainMenu = new StringBuilder();
		StringBuilder userMenu = new StringBuilder();
		StringBuilder auditorMenu = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		mainMenu.append("1. Login").append(newLine).append("2. Exit")
				.append(newLine).append("Enter the choice:");
		userMenu.append("1. Create invoice").append(newLine)
				.append("2. List invoice").append(newLine)
				.append("3. Logout").append(newLine)
				.append("Enter the choice:");
		while (true) {
			System.out.println(mainMenu.toString());
			int choice = new Integer(br.readLine());
			switch (choice) {
			case 1:
				System.out.println("Enter the username:");
				String userName = br.readLine();
				System.out.println("Enter the password:");
				String password = br.readLine();
				if (UserBO.validateUser(userName, password)!= null) {
					logout = false;
					user = UserBO.validateUser(userName, password);
					while (true) {
						System.out.println(userMenu.toString());
						int userChoice = new Integer(br.readLine());
						switch (userChoice) {
						case 1:
							try {
								
								if(user.getRole().equalsIgnoreCase("Auditor"))
									throw new InsufficientPrivilegeException("Permission Denied");
								else{
									System.out.println("Enter the invoice detail:");
								invoiceDetails = br.readLine();
								
								String[] invDe = invoiceDetails.split(",");
								Date inDate= sdf.parse(invDe[2]);
								invoice = new Invoice(invDe[0],Integer.parseInt(invDe[1]),inDate,user);
								
								new ClerkBO().createInvoice(invoice);
								}
								
								//fill code here.
							} catch (Exception e) {
								System.out.println(e.toString());
							}
							break;
						case 2:
							try {
								if(user.getRole().equalsIgnoreCase("Clerk"))
									throw new InsufficientPrivilegeException("Permission Denied");
								else{
								invoiceList= new AuditorBO().listInvoice();
								if (!invoiceList.isEmpty()) {
									System.out.format("%-5s %-15s %-10s %-15s %-15s %s\n","Id", "Invoice number","Amount", "Status","Created by", "Created on");
									for(Invoice inv : invoiceList) {	
										System.out.format("%-5s %-15s %-10s %-15s %-15s %s\n",inv.getId(),inv.getInvoiceNumber(),inv.getAmount(),inv.getStatus(),inv.getCreatedBy().getUserName(),inv.getCreatedDate());	
										
									}								
								}
								}
							} catch (Exception e) {
								System.out.println(e.toString());
							}
							break;
						case 3:
							logout = true;
							break;
						}
						if (logout)
							break;
					}
				} else
					System.out.println("Invalid username or password");
				break;
			case 2:
				System.exit(0);
			}
		}
	}

	/*public static UserBO findRole(User user) {
		String role = user.getRole();
		if (role.equalsIgnoreCase("clerk"))
			//fill code here.
		if (role.equalsIgnoreCase("auditor"))
			//fill code here.
		return null;
	
	}*/
}

