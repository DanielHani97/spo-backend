package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.AssesmentDao;
import my.com.fotia.osdec.assesment.model.Assesment;

@Service
public class AssesmentServiceImpl implements AssesmentService{

	@Autowired
	AssesmentDao assesmentDao;

	@Override
	public Assesment save(Assesment assesment) {
		return assesmentDao.save(assesment);
	}

	@Override
	public Assesment findById(String id) {
		return assesmentDao.findById(id);
	}

	@Override
	public Assesment findByInstanceid(String instaneId) {
		return assesmentDao.findByInstanceid(instaneId);
	}

	@Override
	public List<Assesment> findAll() {
		return assesmentDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		assesmentDao.deleteById(id);
	}

	@Override
	public List<Assesment> findByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase(
			String title, String name, String technology, Pageable pageable) {
		return assesmentDao.findByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase(title, name, technology, pageable);
	}

	@Override
	public long countByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase(
			String title, String name, String technology) {
		return assesmentDao.countByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase(title, name, technology);
	}

	@Override
	public boolean existsByTechnology_NameAndLevelAndIdNot(String technology, String level, String id){
		return assesmentDao.existsByTechnology_NameAndLevelAndIdNot(technology, level, id);
	}

	@Override
	public List<Assesment> findByTechnology_IdAndLevel(String technologyId, String level) {
		return assesmentDao.findByTechnology_IdAndLevel(technologyId, level);
	}

	@Override
	public List<Assesment> findQuestionLimit(String techId, String level, int limit) {
		return assesmentDao.findQuestionLimit(techId, level, limit);
	}

	@Override
	public boolean existsByTechnology_NameAndLevel(String technology, String level) {
		return assesmentDao.existsByTechnology_NameAndLevel(technology, level);
	}

}
