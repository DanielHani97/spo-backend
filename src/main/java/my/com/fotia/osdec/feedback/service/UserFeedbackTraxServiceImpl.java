package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.UserFeedbackDao;
import my.com.fotia.osdec.feedback.dao.UserFeedbackTraxDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.UserFeedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;

@Service
public class UserFeedbackTraxServiceImpl implements UserFeedbackTraxService{

	@Autowired
	UserFeedbackTraxDao userFeedbackTraxDao;

	@Override
	public void save(UserFeedbackTrax userFeedbackTrax) {
		userFeedbackTraxDao.save(userFeedbackTrax);
	}

	@Override
	public void save(List<UserFeedbackTrax> ls) {
		userFeedbackTraxDao.save(ls);
	}

	@Override
	public long count() {
		return userFeedbackTraxDao.count();
	}

	@Override
	public UserFeedbackTrax findById(String id) {
		return userFeedbackTraxDao.findById(id);
	}

	@Override
	public UserFeedbackTrax findByType(String type) {
		return userFeedbackTraxDao.findByType(type);
	}

	@Override
	public List<UserFeedbackTrax> findAll() {
		return userFeedbackTraxDao.findAll();
	}

	@Override
	public boolean existsByParentidAndInstanceidAndUser_Id(String parentId, String instanceId, String userId) {
		return userFeedbackTraxDao.existsByParentidAndInstanceidAndUser_Id(parentId, instanceId, userId);
	}

	@Override
	public List<UserFeedbackTrax> findByParentidAndInstanceidAndUser_IdAndType(String parentId, String instanceId,
			String userId, String type) {
		return userFeedbackTraxDao.findByParentidAndInstanceidAndUser_IdAndType(parentId, instanceId, userId, type);
	}

	@Override
	public boolean existsByParentidAndUser_IdAndType(String parentId, String userId, String type) {
		return userFeedbackTraxDao.existsByParentidAndUser_IdAndType(parentId, userId, type);
	}

	@Override
	public List<UserFeedbackTrax> findByParentidAndUser_IdAndType(String parentId, String userId, String type) {
		return userFeedbackTraxDao.findByParentidAndUser_IdAndType(parentId, userId, type);
	}

	@Override
	public boolean existsByParentidAndUser_IdAndCreatedby_IdAndType(String parentId, String userId, String createdby,
			String type) {
		return userFeedbackTraxDao.existsByParentidAndUser_IdAndCreatedby_IdAndType(parentId, userId, createdby, type);
	}


}
