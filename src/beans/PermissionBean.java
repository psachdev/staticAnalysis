package beans;

import java.util.ArrayList;

public class PermissionBean {
	private String permission;
	private Boolean is_external;
	private ArrayList<String> externalPackages;
	private String description;
	private String group;
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Boolean getIs_external() {
		return is_external;
	}
	public void setIs_external(Boolean is_external) {
		this.is_external = is_external;
	}
	public ArrayList<String> getExternalPackages() {
		return externalPackages;
	}
	public void setExternalPackages(ArrayList<String> externalPackages) {
		this.externalPackages = externalPackages;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int compareTo(PermissionBean o2) {
		//System.out.println ("Compare To :" + count + " : y = " + o2.count);
		return this.group.equals(o2.getGroup()) == true ? 1 : 0;
	} 
}