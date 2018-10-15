package my.com.fotia.osdec.general.manday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="mandayUsed")
public class MandayUsed {

	@Id
	private String id;
	
	@Column(nullable=false)
	private int usedMainday;
	
	private String created_by;
	private String modified_by;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date created_date;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date modified_date;

	
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	
	}

	public int getUsedMainday() {
		return usedMainday;
	
	}

	public void setUsedMainday(int usedMainday) {
		this.usedMainday = usedMainday;
	
	}

	public String getCreated_by() {
		return created_by;
	
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

	public Date getCreated_date() {
		return created_date;
	
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	
	}

	public Date getModified_date() {
		return modified_date;
	
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	
	}
	
	
}
