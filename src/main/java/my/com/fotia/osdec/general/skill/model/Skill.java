package my.com.fotia.osdec.general.skill.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;


@Entity
@Table(name="skill")

public class Skill {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private Technology technology;
	
	private String level;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
	@JsonBackReference
	private User user;
	
	private double mark;
	
	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
