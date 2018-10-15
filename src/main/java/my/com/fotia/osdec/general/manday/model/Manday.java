package my.com.fotia.osdec.general.manday.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "manday")
public class Manday {

	@Id
	@Column(nullable = false, columnDefinition="Varchar(100)")
	private String id;
	private String category;
	private int total;
	private String mandayReserved;
	
	@OneToMany
	private List<MandayUsed> mandayls;
	
	public String getId() {
		return id;
	}
	
	private int mandayUsed;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<MandayUsed> getMandayls(){
		return mandayls;
	}
	
	public String getMandayReserved() {
		return mandayReserved;
	}

	public void setMandayReserved(String mandayReserved) {
		this.mandayReserved = mandayReserved;
	}

	public void setMandayls(List<MandayUsed> mandayls) {
		this.mandayls = mandayls;
	}

	public int getMandayUsed() {
		return mandayUsed;
	}

	public void setMandayUsed(int mandayUsed) {
		this.mandayUsed = mandayUsed;
	}
	
}
