package bank_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
	Connection con;
	Scanner sc;
	public AccountManager(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	
	
	public void debit(long account_number) throws SQLException {
		System.out.println("enter amount to debit");
		double amount=sc.nextDouble();
		System.out.println("enter your security pin");
		int pin=sc.nextInt();
		
		String query="select balance from accounts where account_number=? and security_pin=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement p=con.prepareStatement(query);
			p.setLong(1, account_number);
			p.setInt(2, pin);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				double balance=rs.getDouble("balance");
				if(amount<balance) {
					String q1="update accounts set balance=balance-? where account_number=? and security_pin=?";
					PreparedStatement p1=con.prepareStatement(q1);
					p1.setDouble(1, amount);
					p1.setLong(2, account_number);
					p1.setInt(3, pin);
					int rows=p1.executeUpdate();
					if(rows>0) {
						System.out.println("amount has been debited successfully"+amount);
						con.commit();
					}
					else {
						System.out.println("transaction failed");
						con.rollback();
					}
				}
				else
					System.out.println("Insufficient balance");
				
			}
			else {
				System.out.println("invalid security pin");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			con.setAutoCommit(true);
		}
	}
	
	
	
	
	
	public void credit(long account_number) throws SQLException {
		System.out.println("enter amount to debit");
		double amount=sc.nextDouble();
		System.out.println("enter your security pin");
		int pin=sc.nextInt();
		
		String query="select balance from accounts where account_number=? and security_pin=?";
		try {
			con.setAutoCommit(false);
			PreparedStatement p=con.prepareStatement(query);
			p.setLong(1, account_number);
			p.setInt(2, pin);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				double balance=rs.getDouble("balance");
				if(amount<balance) {
					String q1="update accounts set balance=balance+? where account_number=? and security_pin=?";
					PreparedStatement p1=con.prepareStatement(q1);
					p1.setDouble(1, amount);
					p1.setLong(2, account_number);
					p1.setInt(3, pin);
					int rows=p1.executeUpdate();
					if(rows>0) {
						System.out.println("amount has been credited successfully"+amount);
						con.commit();
						return;
					}
					else {
						System.out.println("transaction failed");
						con.rollback();
					
					}
				}
				else
					System.out.println("Insufficient balance");
				
				
			}
			else {
				System.out.println("invalid security pin");
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			con.setAutoCommit(true);
		}
	}
	
	
	
	
	
	
	public void transferMoney(long account_num) throws SQLException {
		sc.nextLine();
		System.out.println("enter amount to transfer");
		double amount=sc.nextDouble();
		System.out.println("enter receiver account number");
		long rec_accountNum=sc.nextLong();
		System.out.println("enter security pin");
		int pin=sc.nextInt();
		String query="select balance from accounts where account_number=? and security_pin=?";
		try {
			con.setAutoCommit(false);
			if(account_num!=0&&rec_accountNum!=0) {
			PreparedStatement p=con.prepareStatement(query);
			p.setLong(1,account_num);
			p.setInt(2, pin);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				double balance=rs.getDouble("balance");
				if(amount<=balance) {
					String q1="update accounts set balance=balance-? where account_number=? and security_pin=?";
					String q2="update accounts set balance=balance+? where account_number=?";
					PreparedStatement p1=con.prepareStatement(q1);
					PreparedStatement p2=con.prepareStatement(q2);
					p1.setDouble(1, amount);
					p2.setDouble(1, amount);
					p1.setLong(2, account_num);
					p1.setInt(3, pin);
					p2.setLong(2, rec_accountNum);
					int row1=p1.executeUpdate();
					int row2=p2.executeUpdate();
					if(row1>0&&row2>0) {
						System.out.println("amount has been transferred successfully");
						con.commit();
					}
					else {
						System.out.println("transaction failed");
						con.rollback();
					}
				}
				else {
					System.out.println("Insufficient balance");
				}
			}
			else {
				System.out.println("invalid security pin");
			}
		}
			else {
				System.out.println("Invalid account number");
			}	
		
	}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			con.setAutoCommit(true);
		}
	
}
	
	
	
	
	
	public void getBalance(long account_num) {
		sc.nextLine();
		System.out.println("enter security pin");
		int pin=sc.nextInt();
		String q="select balance from accounts where account_number=? and security_pin=?";
		try {
			if(account_num!=0) {
				PreparedStatement p=con.prepareStatement(q);
				p.setLong(1, account_num);
				p.setInt(2, pin);
				ResultSet rs=p.executeQuery();
				if(rs.next()) {
					double balance=rs.getDouble("balance");
					System.out.println("your balance is "+balance);
					
				}
				else {
					System.out.println("invalid balance");
				}
			}
			else {
				System.out.println("invalid account ");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
