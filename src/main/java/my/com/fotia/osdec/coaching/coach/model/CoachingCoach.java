package my.com.fotia.osdec.coaching.coach.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "coachingCoach")
public class CoachingCoach {
	
	@Id
	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String id;
	
	@OneToOne
	private Coaching coaching;
	
	@OneToOne
	private User coach;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Coaching getCoaching() {
		return coaching;
	}

	public void setCoaching(Coaching coaching) {
		this.coaching = coaching;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	
	
}
