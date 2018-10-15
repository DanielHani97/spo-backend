package my.com.fotia.osdec.general.project.service;

import java.util.List;

import my.com.fotia.osdec.general.project.model.Project;

public interface ProjectService {

	Project findByName(String name);
	Project findById(String id);
	List<Project> projectList();
	List<Project> findAll();

	
	void save(Project project);
	void deleteById(String id);
	void deleteByUserId(String user_id);
}
