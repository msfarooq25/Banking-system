package bank_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;



public class Accounts {
	Connection con;
	Scanner sc;
	public Accounts(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	public long openAccount(String email) {
		if(!account_exist(email)) {
		sc.nextLine();
		System.out.println("enter fullName");
		String name=sc.nextLine();
		System.out.println("enter security pin");
		int pin=sc.nextInt();
		System.out.println("enter initial balance");
		double bal=sc.nextDouble();
		String query="insert into accounts values(?,?,?,?,?)";
		try {
			long account_number=generateAccountNum();
			PreparedStatement p=con.prepareStatement(query);
			p.setString(1, name);
			p.setString(2,email);
			p.setLong(3, account_number);
			p.setInt(4, pin);
			p.setDouble(5, bal);
			int rows=p.executeUpdate();
			if(rows>0) {
				return account_number;
			}
			else {
				throw new RuntimeException("Account creation failed");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
		throw new RuntimeException("AccountAlreadyExist");
		
	}
	public long generateAccountNum() {
		try {
		Statement s=con.createStatement();
		ResultSet rs=s.executeQuery("select account_number from accounts order by account_number desc limit 1");
		if(rs.next()) {
			long lastAccountNum=rs.getLong("account_number");
			return lastAccountNum+1;
		}
		else {
			return 1001001;
		}
	
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			return 1001001;
		
	}
	
	public long getAccountNum(String email) {
		String query="select account_number from accounts where email=?";
		try {
			PreparedStatement p=con.prepareStatement(query);
			p.setString(1, email);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				return rs.getLong("account_number");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("account number does not exist");
	}
	
	
	
	public boolean account_exist(String email) {
		String query="select * from accounts where email=?";
		try {
			PreparedStatement p=con.prepareStatement(query);
			p.setString(1, email);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
