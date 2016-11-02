package com.yanis.getmyphonenumber;


public class PhoneInfo {  //信息封装类
	long id;
	private String phoneName;
	private String phoneNumber;

	public PhoneInfo(String phoneName,  long id) {
		setPhoneName(phoneName);
		setPhoneNumber(phoneNumber);
		setContactId(id);
		this.id = id;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setContactId(long id){

		this.id = id;
	}

	public long getContactId()
	{
		return id;
	}
}
