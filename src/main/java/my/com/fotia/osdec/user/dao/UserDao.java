package my.com.fotia.osdec.user.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.user.model.User;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

	User findByName(String name);
	User findByEmail(String email);
	User findById(String id);
	List<User> findAll();

	@Modifying
	@Query("DELETE from User where id = :id")
	void deleteById(@Param("id") String id);
	
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    User findByUsername(String username);
    
    List<User> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase
	(String name, String username, String email, String agencyName, String position, Pageable pageable);
    
    long countByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase
	(String name, String username, String email, String agencyName, String position);
    
    List<User> findByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled
	(String name, boolean status1, String username,boolean status2, String email, boolean status3,String agencyName, boolean status, Pageable pageable);
    
    long countByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled
	(String name, boolean status1, String username,boolean status2, String email, boolean status3,String agencyName, boolean status4);
    
    @Modifying
	@Query("UPDATE User SET enabled = :status where id = :id")
	void updateStatus(@Param("status") boolean status, @Param("id") String id);
}
