package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.UserAssesmentQuestion;

@Transactional
public interface UserAssesmentQuestionDao extends JpaRepository<UserAssesmentQuestion, Long> {

	UserAssesmentQuestion findById(String id);
	List<UserAssesmentQuestion> findAll();
	
	@Modifying
	@Query("DELETE from UserAssesmentQuestion where id = :id")
	void deleteById(@Param("id") String id);
}
