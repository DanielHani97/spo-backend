package my.com.fotia.osdec.coaching.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "coachingUser")
public class CoachingUser {
	
	@Id
	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String id;
	
	@OneToOne
	private Coaching coaching;
	
	@OneToOne
	private User user;
	
	@Column(columnDefinition = "TEXT")
	private String coach_remarks;
	
	@Column(columnDefinition = "TEXT")
	private String admin_remarks;
	
	@Column(columnDefinition = "CHAR")
	private String role;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCoach_remarks() {
		return coach_remarks;
	}

	public void setCoach_remarks(String coach_remarks) {
		this.coach_remarks = coach_remarks;
	}

	public String getAdmin_remarks() {
		return admin_remarks;
	}

	public void setAdmin_remarks(String admin_remarks) {
		this.admin_remarks = admin_remarks;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
