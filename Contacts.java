/**
 * 
 */
package phonebookGUI;

import java.io.Serializable;

/**
 * 2025Äê2ÔÂ27ÈÕ by @author 18212
 *
 * 
 */
public class Contacts implements Serializable {
	
	private String name;
	private String sex;
	private String phoneNo;
	
	public Contacts() {}
	
	public Contacts(String name, String phoneNo, String sex) {
		this.name = name;
		this.sex = sex;
		this.phoneNo = phoneNo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String toString() {
		return "[name=" + name + ",sex="+ sex + ",phoneNo=" + phoneNo + "]";
	}
	
	public int hashCode() {
		final int prime = 31;
		int result =1;
		result = prime * result + ((name == null)? 0 : name.hashCode());
		result = prime * result + ((phoneNo == null)? 0 : phoneNo.hashCode());
		result = prime * result + ((sex == null)? 0 : sex.hashCode());
		return result;
	}
	
	public  boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Contacts other = (Contacts) obj;
		if(name == null) {
			if(other.name != null) return false;
		}else if(!name.equals(other.name)) return false;
		if(phoneNo != other.phoneNo) return false;
		if(sex == null) {
			if(other.sex != null) return false;
		}else if (!sex.equals(other.sex)) return false;
		
		return true;
	}
}
