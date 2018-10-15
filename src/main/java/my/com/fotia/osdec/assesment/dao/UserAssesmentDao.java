package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.UserAssesment;

@Transactional
public interface UserAssesmentDao extends JpaRepository<UserAssesment, Long> {

	UserAssesment findById(String id);
	UserAssesment findByUserId(String userId);
	List<UserAssesment> findAll();
	
	@Modifying
	@Query("DELETE from UserAssesment where id = :id")
	void deleteById(@Param("id") String id);
	
	boolean existsByTechnology_IdAndLevelAndUserId(String technology, String level, String userId);
}
