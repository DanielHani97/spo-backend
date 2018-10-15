package my.com.fotia.osdec.assesment.model;

public class AssesmentCollection {

	private Assesment assesment;
	private UserAssesmentTrax[] userAssesmentTrax;
	public Assesment getAssesment() {
		return assesment;
	}
	public void setAssesment(Assesment assesment) {
		this.assesment = assesment;
	}
	public UserAssesmentTrax[] getUserAssesmentTrax() {
		return userAssesmentTrax;
	}
	public void setUserAssesmentTrax(UserAssesmentTrax[] userAssesmentTrax) {
		this.userAssesmentTrax = userAssesmentTrax;
	}
}
