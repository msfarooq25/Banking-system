package bank_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankingApp {
	public static void main(String[] args) {
		Connection con=null;
		Scanner sc;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankmanagement?user=root&password=Farooq@25");
			sc=new Scanner(System.in);
			User user=new User(con, sc);
			Accounts accounts=new Accounts(con, sc);
			AccountManager accountManager=new AccountManager(con, sc);
			long account_number;
			String email;
			while(true) {
				System.out.println("WELCOME TO BANKING SYSTEM");
				System.out.println("1.Register");
				System.out.println("2.logIn");
				System.out.println("3.Exit");
				System.out.println("Enter Your Choice");
				int choice1=sc.nextInt();
				switch(choice1) {
				case 1:user.register();
						break;
				case 2:
					email=user.logIn();
					if(email!=null) {
						System.out.println();
						System.out.println("user looged in successfully");
						if(!(accounts.account_exist(email))) {
							System.out.println();
							System.out.println("1.open an account");
							System.out.println("2.exit");
							if(sc.nextInt()==1) {
								account_number=accounts.openAccount(email);
								if(account_number>0) {
									System.out.println("Your account Number is"+account_number);
								}
								else {
									System.out.println("account creation failed");
								}
								
							}
							else
								break;
							
						}
						account_number=accounts.getAccountNum(email);
						int choice2=0;
						while(choice2!=5) {
							sc.nextLine();
						System.out.println("1.debit money");
						System.out.println("2.credit money");
						System.out.println("3.transfer money");
						System.out.println("4.get balance");
						System.out.println("5.exit");
						
							choice2=sc.nextInt();
							switch (choice2) {
							case 1: 
								accountManager.debit(account_number);
								break;
							case 2:
								accountManager.credit(account_number);
								break;
							case 3:
								accountManager.transferMoney(account_number);
								break;
							case 4:
								accountManager.getBalance(account_number);
								break;
							case 5:
								break;
									
							default:
								System.out.println("invalid choice");
							}
						}
						
						
						
					}
					else {
						System.out.println("invalid email or password");
					}
					
				case 3:
					break;
				
				}
			}	
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
