package my.com.fotia.osdec.trainingCoach.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="trainingCoach")
public class TrainingCoach {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User coach;
	
	@ManyToOne
	private Training training;

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

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	

}
