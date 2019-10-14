package com.zhanghao.po;

import java.sql.*;
import java.lang.*;

public class Student{
	private Integer ID;
	private Enum age;
	private String Name;
	public Student(){
	}
	public void setID(Integer ID){
		this.ID=ID;
	}
	public void setAge(Enum age){
		this.age=age;
	}
	public void setName(String Name){
		this.Name=Name;
	}
	public Integer getID(){
		return ID;
	}
	public Enum getAge(){
		return age;
	}
	public String getName(){
		return Name;
	}
}