package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.UserAssesmentTrax;

@Transactional
public interface UserAssesmentTraxDao extends JpaRepository<UserAssesmentTrax, Long> {

	UserAssesmentTrax findById(String id);
	List<UserAssesmentTrax> findAll();
	long countByMark(int mark);
	List<UserAssesmentTrax> findByUserAssesId(String userAssesId);
	List<UserAssesmentTrax> findByAnswerId(String answerId);
	List<UserAssesmentTrax> findByQuestionId(String questionId);
}
