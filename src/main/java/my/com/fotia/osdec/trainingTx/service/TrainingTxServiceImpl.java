package my.com.fotia.osdec.trainingTx.service;

import java.util.List;
import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.trainingTx.dao.TrainingTxDao;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.user.model.User;

@Service
public class TrainingTxServiceImpl implements TrainingTxService {
	
	@Autowired
	TrainingTxDao trainingTxDao;
	
	public TrainingTx findById(String id) {
		return trainingTxDao.findById(id);
	}

	public List<TrainingTx> trainingTxList() {
		return trainingTxDao.findAll();
	}

	@Override
	public void save(TrainingTx trainingTx) {
		trainingTxDao.save(trainingTx);
	}

	@Override
	public void deleteById(String id) {
		trainingTxDao.deleteById(id);
	}
	@Override
	public List<TrainingTx> findByUser(User user) {
		// TODO Auto-generated method stub
		return trainingTxDao.findByUser(user);
	}
	
	@Override
	public List<TrainingTx> findByTraining_id(String id) {
		// TODO Auto-generated method stub
		return trainingTxDao.findByTraining_id(id);
	}
	
	@Override
	public List<TrainingTx> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase(String name, String name2, String title, Pageable pageable) {
		
		return trainingTxDao.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase(name,  name2, title, pageable);
	}
	
	@Override
	public List<TrainingTx> findByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(
			String userid, String title, String userid2, String technology, String userid3, String duration, String userid4, String place, Pageable pageable) {
		
		return trainingTxDao.findByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(userid, title, userid2, technology, userid3, duration, userid4, place, pageable);
	}

	@Override
	public long countByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(
			String user, String title, String user2, String technology, String user3, String duration, String user4, String place) {
		
		return trainingTxDao.countByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(user, title, user2, technology, user3, duration, user4, place);
	}

	@Override
	public long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase( String name, String name2, String title) {
		
		return trainingTxDao.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase(name, name2, title);
	}

	@Override
	public boolean existsByUser_IdAndTraining_IdAndStatus(String userId, String trainingId, String status) {
		return trainingTxDao.existsByUser_IdAndTraining_IdAndStatus(userId, trainingId, status);
	}

	@Override
	public List<TrainingTx> findByUser_Id(String id) {
		return trainingTxDao.findByUser_Id(id);
	}
	
	@Override
	public List<TrainingTx> findByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(
			String trid, String user, String trid2, String agency, String trid3, String title, String trid4, String status, Pageable pageable) {
		
		return trainingTxDao.findByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(trid, user, trid2, agency, trid3, title, trid4, status, pageable);
	}

	@Override
	public long countByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(
			String tr, String user, String tr2, String agency, String tr3, String title, String tr4, String status) {
		
		return trainingTxDao.countByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(tr, user, tr2, agency, tr3, title, tr4, status);
	}
	
	@Override
	public List<TrainingTx> findAll() {
		return trainingTxDao.findAll();
	}
	
	@Override 
	  public List<TrainingTx> findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2, 
	      Pageable pageable) { 
	    return trainingTxDao.findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, pageable); 
	  } 
	 
	  @Override 
	  public Long countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2) { 
	    return trainingTxDao.countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2); 
	  }

	@Override
	public TrainingTx findByTraining_IdAndUser_IdAndStatusNotAndStatusNot(String trainingId, String userId, String status, String status2) {
		return trainingTxDao.findByTraining_IdAndUser_IdAndStatusNotAndStatusNot(trainingId, userId, status, status2);
	}

	@Override
	public List<TrainingTx> findByTraining_idAndStatusNotAndStatusNot(String training_id, String status, String status2) {
		return trainingTxDao.findByTraining_idAndStatusNotAndStatusNot(training_id, status,status2);
	}

	@Override
	public List<TrainingTx> findByTraining_idAndStatus(String id, String string) {
		return trainingTxDao.findByTraining_idAndStatus(id, string);
	} 
}
