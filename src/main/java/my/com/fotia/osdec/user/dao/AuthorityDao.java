package my.com.fotia.osdec.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.com.fotia.osdec.user.model.Authority;

public interface AuthorityDao extends JpaRepository<Authority,Long>{

	Authority findById(String id);
	Authority findByName(String name);
	List<Authority> findAll();
}
