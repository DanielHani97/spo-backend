package my.com.fotia.osdec.general.certificate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="certificate")
public class Certificate {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	private String name;
	private String category;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Filestorage filestorage;
	
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Filestorage getFilestorage() {
		return filestorage;
	}

	public void setFilestorage(Filestorage filestorage) {
		this.filestorage = filestorage;
	}
}
