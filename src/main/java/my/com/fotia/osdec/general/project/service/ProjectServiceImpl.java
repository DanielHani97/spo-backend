package my.com.fotia.osdec.general.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.project.dao.ProjectDao;
import my.com.fotia.osdec.general.project.model.Project;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	ProjectDao projectDao;

	public Project findByName(String name) {
		return projectDao.findByName(name);
	}

	public Project findById(String id) {
		return projectDao.findById(id);
	}

	public List<Project> projectList() {
		return projectDao.findAll();
	}

	@Override
	public void save(Project project) {
		projectDao.save(project);
	}

	@Override
	public void deleteById(String id) {
		projectDao.deleteById(id);
	}
	
	@Override
	public void deleteByUserId(String user_id) {
		projectDao.deleteByUserId(user_id);
	}
	
	@Override
	public List<Project> findAll() {
		return projectDao.findAll();
	}

}
