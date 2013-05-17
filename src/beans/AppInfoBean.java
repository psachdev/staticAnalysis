package beans;

public class AppInfoBean {
	/* This information is read from AppInfo Table */
	private Long id;
	private String appname;
	private String packagename;
	private Double rating_value;
	private Integer installs_max;
	private Integer installs_min;
	private String category;
	private String imageSrc;
	
	
	
	public AppInfoBean () {}
	
	public String getImageSrc (){
		return imageSrc;
	}
	
	public void setImageSrc (String srcLink) {
		imageSrc = srcLink;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public Double getRating_value() {
		return rating_value;
	}
	public void setRating_value(Double rating_value) {
		this.rating_value = rating_value;
	}
	public Integer getInstalls_max() {
		return installs_max;
	}
	public void setInstalls_max(Integer installs_max) {
		this.installs_max = installs_max;
	}
	public Integer getInstalls_min() {
		return installs_min;
	}
	public void setInstalls_min(Integer installs_min) {
		this.installs_min = installs_min;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
