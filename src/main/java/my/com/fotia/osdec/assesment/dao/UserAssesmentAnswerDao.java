package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.AssesmentAnswer;
import my.com.fotia.osdec.assesment.model.UserAssesmentAnswer;

@Transactional
public interface UserAssesmentAnswerDao extends JpaRepository<UserAssesmentAnswer, Long> {

	UserAssesmentAnswer findById(String id);
	List<UserAssesmentAnswer> findAll();
	
	@Modifying
	@Query("DELETE from UserAssesmentAnswer where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from UserAssesmentAnswer where user_assesment_question_id = :assesid")
	void deleteByAssesId(@Param("assesid") String assesid);
}
