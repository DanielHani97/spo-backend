package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.Assesment;

@Transactional
public interface AssesmentDao extends JpaRepository<Assesment, Long> {

	Assesment findById(String id);
	Assesment findByInstanceid(String instaneId);
	List<Assesment> findAll();
	
	@Modifying
	@Query("DELETE from Assesment where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Assesment> findByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase
	(String title,String name, String technology, Pageable pageable);
	
	long countByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase
	(String title,String name, String technology);
	
	boolean existsByTechnology_NameAndLevelAndIdNot(String technology, String level, String id);
	
	boolean existsByTechnology_NameAndLevel(String technology, String level);
	
	List<Assesment> findByTechnology_IdAndLevel(String technologyId, String level);
	
	@Modifying
	@Query(value="SELECT * FROM Assesment where technology_id = :techId AND level = :level ORDER BY RAND() LIMIT :limit", nativeQuery = true)
	List<Assesment> findQuestionLimit(@Param("techId") String techId, @Param("level") String level, @Param("limit") int limit);
}
