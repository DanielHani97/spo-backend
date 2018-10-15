package my.com.fotia.osdec.general.skill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.skill.dao.SkillDao;
import my.com.fotia.osdec.general.skill.model.Skill;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	SkillDao skillDao;

	

	public Skill findById(String id) {
		return skillDao.findById(id);
	}

	public List<Skill> skillList() {
		return skillDao.findAll();
	}

	@Override
	public void save(List<Skill> ls) {
		skillDao.save(ls);
	}

	@Override
	public void deleteById(String id) {
		skillDao.deleteById(id);
	}

	@Override
	public List<Skill> findByUser_Id(String userid) {
		return skillDao.findByUser_Id(userid);
	}

	@Override
	public Skill findByUser_IdAndTechnology_IdAndLevel(String userId, String technologyId, String level) {
		return skillDao.findByUser_IdAndTechnology_IdAndLevel(userId, technologyId, level);
	}

	@Override
	public void save(Skill skill) {
		skillDao.save(skill);
	}

}
