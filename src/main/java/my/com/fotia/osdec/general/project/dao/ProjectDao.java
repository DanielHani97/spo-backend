package my.com.fotia.osdec.general.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.project.model.Project;

@Transactional
public interface ProjectDao extends CrudRepository<Project, Long> {

	Project findByName(String name);
	Project findById(String id);
	List<Project> findAll();
	
	@Modifying
	@Query("DELETE from Project where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from Project where user_id =?1 ")
	void deleteByUserId(@Param("user_id") String user_id);
	
}
