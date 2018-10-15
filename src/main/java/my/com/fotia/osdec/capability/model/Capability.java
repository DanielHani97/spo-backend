package my.com.fotia.osdec.capability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import my.com.fotia.osdec.technology.model.Technology;

@Entity
@Table(name = "capability")
public class Capability {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	private String name;
	
	@OneToOne
	private Technology kepakaran;
	
	private String status;
	
	@Column(columnDefinition="TEXT")
	private String remarks;
	
	private String duration;
	
	private String limitation;
	private String limitation_used;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date starting_date;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date ending_date;
	
	private String created_by;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date created_date;
	
	private String modified_by;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date modified_date;

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

	public Technology getKepakaran() {
		return kepakaran;
	}

	public void setKepakaran(Technology kepakaran) {
		this.kepakaran = kepakaran;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLimitation() {
		return limitation;
	}

	public void setLimitation(String limitation) {
		this.limitation = limitation;
	}

	public String getLimitation_used() {
		return limitation_used;
	}

	public void setLimitation_used(String limitation_used) {
		this.limitation_used = limitation_used;
	}

	public Date getStarting_date() {
		return starting_date;
	}

	public void setStarting_date(Date starting_date) {
		this.starting_date = starting_date;
	}

	public Date getEnding_date() {
		return ending_date;
	}

	public void setEnding_date(Date ending_date) {
		this.ending_date = ending_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	
}
