package com.axis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleid;
    private String name;
    private int userid;
 
    public Roles() {
		super();
	}

	public Roles(String name, int userid) {
		super();
		this.name = name;
		this.userid = userid;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Roles [roleid=" + roleid + ", name=" + name + ", userid=" + userid + "]";
	}
	
}
