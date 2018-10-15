package my.com.fotia.osdec.coaching.activities.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.user.model.User;

import javax.persistence.OneToOne;

@Entity
@Table(name = "coachingActivities")
public class CoachingActivities {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private Coaching coaching;
	
	private String name;
	private String attendance;
	private String venue;
	private String duration;
	private String kelulusan;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date start_date;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date end_date;
	
	@Transient
	private List<User> userLs;
	
	@Transient
	private boolean isDone;
	
	@Transient
	private String instanceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getKelulusan() {
		return kelulusan;
	}

	public void setKelulusan(String kelulusan) {
		this.kelulusan = kelulusan;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Coaching getCoaching() {
		return coaching;
	}

	public void setCoaching(Coaching coaching) {
		this.coaching = coaching;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public List<User> getUserLs() {
		return userLs;
	}

	public void setUserLs(List<User> userLs) {
		this.userLs = userLs;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
