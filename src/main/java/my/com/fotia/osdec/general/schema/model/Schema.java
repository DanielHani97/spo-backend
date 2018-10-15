package my.com.fotia.osdec.general.schema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="schema")

public class Schema {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	private String name;
	private int seniority;
	private String status;
	
	
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
	
	public int getSeniority() {
		return seniority;
	}
	
	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
