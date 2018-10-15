package my.com.fotia.osdec.technology.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import my.com.fotia.osdec.technology.dao.TechnologyDao;
import my.com.fotia.osdec.technology.model.Technology;

@Service
public class TechnologyServiceImpl implements TechnologyService{
	
	@Autowired
	TechnologyDao technologyDao;
	
	public Technology findByName(String name) {
		return technologyDao.findByName(name);
	}

	public Technology findById(String id) {
		return technologyDao.findById(id);
	}

	public List<Technology> technologyList() {
		return technologyDao.findAll();
	}

	@Override
	public void save(Technology technology) {
		technologyDao.save(technology);
	}

	@Override
	public void deleteById(String id) {
		technologyDao.deleteById(id);
	}
	
	@Override
	public List<Technology> findAllTeknologi(String status) {
		return technologyDao.findAllTeknologi(status);
	}

	@Override
	public List<Technology> findFrontend(String type, String status) {
		return technologyDao.findFrontend(type, status);
	}

	@Override
	public List<Technology> findBackend(String type, String status) {
		// TODO Auto-generated method stub
		return technologyDao.findBackend(type, status);
	}

	@Override
	public List<Technology> findDatabase(String type, String status) {
		// TODO Auto-generated method stub
		return technologyDao.findDatabase(type, status);
	}

	@Override
	public List<Technology> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase(
			String name, String type, String language, Pageable pageable) {
		// TODO Auto-generated method stub
		return technologyDao.findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase(name, type, language, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase(String name,
			String type, String language) {
		// TODO Auto-generated method stub
		return technologyDao.countByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase(name, type, language);
	}

}
