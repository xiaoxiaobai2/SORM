package com.zhanghao.po;

import java.sql.*;
import java.lang.*;

public class Emp{
	private String name;
	private Integer id;
	private Double salary;
	private String department;
	public Emp(){
	}
	public void setName(String name){
		this.name=name;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setSalary(Double salary){
		this.salary=salary;
	}
	public void setDepartment(String department){
		this.department=department;
	}
	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}
	public Double getSalary(){
		return salary;
	}
	public String getDepartment(){
		return department;
	}
}