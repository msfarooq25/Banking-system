package bank_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
	Connection con;
	Scanner sc;
	public User(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	public void register() {
		System.out.println("Enter fullName");
		sc.nextLine();
		String FullName=sc.nextLine();
		
		System.out.println("Enter email");
		String email=sc.next();
		System.out.println("enter Password");
		String password=sc.next();
		String query="insert into user values(?,?,?)";
		try {
			if(!user_exist(email)) {
				PreparedStatement p=con.prepareStatement(query);
				p.setString(1,FullName);
				p.setString(2,email);
				p.setString(3,password);
				int rows=p.executeUpdate();
				if(rows>0) {
					System.out.println("registration successful");
					return;
				}
				else {
					System.out.println("registration failed");
				}
			}
			else {
				System.err.println("user already exists");
			}
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return;
	}
	
	public String logIn() {
		sc.nextLine();
		System.out.println("enter email");
		String email=sc.next();
		System.out.println("enter password");
		String password=sc.next();
		String query="select * from user where email=? and password=?";
		try {
			PreparedStatement p=con.prepareStatement(query);
			p.setString(1,email);
			p.setString(2, password);
			ResultSet rs=p.executeQuery();
			if(rs.next()) {
				return email;
			}
			else
				return null;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean user_exist(String email) {
		String query="select * from user where email=?";
		try {
			PreparedStatement p=con.prepareStatement(query);
			p.setString(1,email);
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
