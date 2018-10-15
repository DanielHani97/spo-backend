package my.com.fotia.osdec.general.grade.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.grade.model.Grade;

@Transactional
public interface GradeDao extends CrudRepository<Grade, Long> {
	
	Grade findByName(String name);
	Grade findById(String id);
	List<Grade> findAll();
	
	List<Grade> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name, String status, Pageable pageable);
	
	@Modifying
	@Query("DELETE from Grade where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String status);
	
	long countBy();
	
}
