package my.com.fotia.osdec.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "appAuthority")
public class AppAuthority {

	@Id
    private String id;
	
	@Column(columnDefinition = "text")
	private String remarks;
	
	@Column(columnDefinition = "text")
	private String adminremarks;
	
	private String status;
	
	@OneToOne
	private User createdby;
	
	@OneToOne
	private User modifiedby;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date modifiedon;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date createdon;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Authority authority;
	
	@Transient
	private String roleid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifiedon() {
		return modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getAdminremarks() {
		return adminremarks;
	}

	public void setAdminremarks(String adminremarks) {
		this.adminremarks = adminremarks;
	}

	public User getCreatedby() {
		return createdby;
	}

	public User getModifiedby() {
		return modifiedby;
	}

	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

	public void setModifiedby(User modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	
}
