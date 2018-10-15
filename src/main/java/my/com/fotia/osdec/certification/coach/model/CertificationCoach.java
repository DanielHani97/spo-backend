package my.com.fotia.osdec.certification.coach.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="certificationCoach")
public class CertificationCoach {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User coach;
	
	@ManyToOne
	private Certification certification;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
	}	

}