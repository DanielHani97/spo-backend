package my.com.fotia.osdec.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "authority")
public class Authority {

    @Id
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

   /* @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> usersLs;*/
    
    @OneToMany(mappedBy="authority",fetch = FetchType.EAGER)
    private List<UserAuthorities> userAuthLs;
    
    private String rolename;

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
}