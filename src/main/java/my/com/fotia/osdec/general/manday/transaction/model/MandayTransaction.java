package my.com.fotia.osdec.general.manday.transaction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import my.com.fotia.osdec.general.manday.model.Manday;

@Entity
@Table(name = "mandayTransaction")
public class MandayTransaction {
	
	@Id
	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String id;
	
	private String type;
	
	private String instanceId;
	
//	@OneToOne
//	private Manday manday;
	
	private int manday;
	
	private String created_by;	
	
	private String modified_by;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date created_date;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date instanceDate;
	
	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

//	public Manday getManday() {
//		return manday;
//	}
//
//	public void setManday(Manday manday) {
//		this.manday = manday;
//	}

	public String getCreated_by() {
		return created_by;
	}

	public int getManday() {
		return manday;
	}

	public void setManday(int manday) {
		this.manday = manday;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	public Date getInstanceDate() {
		return instanceDate;
	}

	public void setInstanceDate(Date instanceDate) {
		this.instanceDate = instanceDate;
	}
	
	
	
}
