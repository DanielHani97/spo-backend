package my.com.fotia.osdec.general.keygen.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tempKeygen")
public class Keygen {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(unique=true)
	private String instanceId;
	
	private String keygen;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date createdDate;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date expiredDate;

	public String getId() {
		return id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getKeygen() {
		return keygen;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setKeygen(String keygen) {
		this.keygen = keygen;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	
}
