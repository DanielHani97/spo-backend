package my.com.fotia.osdec.capability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="capabilityCoach")
public class CapabilityCoach {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User coach;
	
	@OneToOne
	private Capability capability;

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

	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}
	
}
