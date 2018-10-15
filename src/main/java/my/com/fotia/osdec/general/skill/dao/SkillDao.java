package my.com.fotia.osdec.general.skill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.skill.model.Skill;

@Transactional
public interface SkillDao extends CrudRepository<Skill, Long> {

	
	Skill findById(String id);
	List<Skill> findAll();
	
	@Modifying
	@Query("DELETE from Skill where user_id = :id")
	void deleteById(@Param("id") String id);
	
	List<Skill> findByUser_Id(String userid);
	
	Skill findByUser_IdAndTechnology_IdAndLevel(String userId, String technologyId, String level);
}
