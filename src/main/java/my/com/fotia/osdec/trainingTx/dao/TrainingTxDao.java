package my.com.fotia.osdec.trainingTx.dao;

import java.util.List;
import java.util.Collection; 

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface TrainingTxDao extends CrudRepository<TrainingTx, Long> {
	
	List<TrainingTx> findByUser(User user);
	TrainingTx findById(String id);
	List<TrainingTx> findAll();
	
	List<TrainingTx> findByTraining_id(String training_id);
	
	@Modifying
	@Query("DELETE from Training Transaction where id = :id")
	void deleteById(@Param("id") String id);
	
	List<TrainingTx> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase
	(String name, String name2, String title, Pageable pageable);
	
	long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase
	(String name, String name2, String title);

	
	List<TrainingTx> findByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase
	(String userid, String title, String userid2, String technology, String userid3, String duration, String userid4, String place, Pageable pageable);
	
	long countByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);
	
	boolean existsByUser_IdAndTraining_IdAndStatus(String userId, String trainingId, String status);
	
	List<TrainingTx> findByUser_Id(String id);
	
	List<TrainingTx> findByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(
			String trid, String user, String trid2, String agency, String trid3, String title, String trid4, String status, Pageable pageable);
	
	long countByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(
			String tr, String user, String tr2, String agency, String tr3, String title, String tr4, String status);
	
	List<TrainingTx> findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
			Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2, Pageable pageable); 

    Long countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
    		Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2); 
    
    TrainingTx findByTraining_IdAndUser_IdAndStatusNotAndStatusNot(String trainingId, String userId, String status, String status2);
    
    List<TrainingTx> findByTraining_idAndStatusNotAndStatusNot(String training_id, String status, String status2);
    List<TrainingTx> findByTraining_idAndStatus(String id, String string);
}
