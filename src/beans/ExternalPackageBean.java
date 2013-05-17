package beans;

import java.util.List;

public class ExternalPackageBean {


	private String externalPackageName;
	private String externalPackageCategory;
	private List<String> otherPackageNames;
	private String numOtherPackages;
	private String website;
	
	public int count;

	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getExternalPackageCategory() {
		return externalPackageCategory;
	}
	public void setExternalPackageCategory(String externalPackageCategory) {
		this.externalPackageCategory = externalPackageCategory;
	}
	
	public String getExternalPackageName() {
		return externalPackageName;
	}
	public void setExternalPackageName(String externalPackageName) {
		this.externalPackageName = externalPackageName;
	}
	public List<String> getOtherPackageNames() {
		return otherPackageNames;
	}
	public void setOtherPackageNames(List<String> otherPackageNames) {
		this.otherPackageNames = otherPackageNames;
	}
	public String getNumOtherPackages() {
		return numOtherPackages;
	}
	public void setNumOtherPackages(Integer numOtherPackages) {
		this.numOtherPackages = String.valueOf(numOtherPackages);
		count = numOtherPackages;
	}
	
	public int compareTo(ExternalPackageBean o2) {
		//System.out.println ("Compare To :" + count + " : y = " + o2.count);
		return count < o2.count ? 1 : 0;
	}  
}
