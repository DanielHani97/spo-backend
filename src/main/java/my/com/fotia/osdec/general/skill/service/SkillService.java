package my.com.fotia.osdec.general.skill.service;

import java.util.List;

import my.com.fotia.osdec.general.skill.model.Skill;

public interface SkillService {

	Skill findById(String id);
	List<Skill> skillList();
	void save(List<Skill> ls);
	void deleteById(String id);
	List<Skill> findByUser_Id(String userid);
	Skill findByUser_IdAndTechnology_IdAndLevel(String userId, String technologyId, String level);
	void save(Skill skill);
}
