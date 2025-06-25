package com.axis.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name="users")
public class Users extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String address;
	private String phone;
	private int loginAttempts;
	private boolean blocked;
	
	public Users() {
		super("users","users",new ArrayList<>());
	}
	
	public Users(String username, String password, Collection<? extends GrantedAuthority> authorities,String un,String pw,String em,String ad,String ph) {
		super(username, password, authorities);
		this.username=un;
		this.password=pw;
		this.email=em;
		this.address=ad;
		this.phone=ph;
	}
	
	 // Add this constructor without the authorities parameter
	   public Users(String username, String password, String email, String address, String phone, int loginAttempts, boolean blocked) {
	        super(username, password, new ArrayList<>());
	        this.username = username;
	        this.password = password;
	        this.email = email;
	        this.address = address;
	        this.phone = phone;
	        this.loginAttempts = loginAttempts;
	        this.blocked = blocked;
	    }
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public String toString() {
		return "Username - "+username+"\nPassword - "+password+"\nEmail    - "+email+"\nAddress  - "+address+"\nPhone no - "+phone;
	}
	
}
