package my.com.fotia.osdec.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.user.model.User;

public interface UserService {
	
	User findByName(String name);
	User findByEmail(String email);
	User findById(String id);
	List<User> userList();
	List<User> findAll();
	
	void save(User user);
	void deleteById(String id);
	
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
    
    void updateStatus(@Param("status") boolean status, @Param("id") String id);

}
