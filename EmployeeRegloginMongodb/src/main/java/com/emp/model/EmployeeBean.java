package com.emp.model;

public class EmployeeBean {

	private String _id;
	private String email;
	private String userName;
	private String mobile;
	private String password;
	
	public EmployeeBean() {
	}

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "EmployeeBean [id=" + _id + ", email=" + email + ", userName=" + userName + ", mobile=" + mobile
				+ ", password=" + password + "]";
	}
	
}
