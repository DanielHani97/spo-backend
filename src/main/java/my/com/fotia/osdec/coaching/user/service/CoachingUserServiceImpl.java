package my.com.fotia.osdec.coaching.user.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.coaching.user.dao.CoachingUserDao;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.user.model.User;

@Service
public class CoachingUserServiceImpl implements CoachingUserService {
	
	@Autowired
	CoachingUserDao coachingUserDao;

	@Override
	public CoachingUser findById(String id) {
		// TODO Auto-generated method stub
		return coachingUserDao.findById(id);
	}

	@Override
	public List<CoachingUser> coachingUserList() {
		// TODO Auto-generated method stub
		return coachingUserDao.findAll();
	}

	@Override
	public void save(CoachingUser coachingUser) {
		// TODO Auto-generated method stub
		coachingUserDao.save(coachingUser);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		coachingUserDao.deleteById(id);
	}

	@Override
	public List<CoachingUser> findByUser(User user) {
		// TODO Auto-generated method stub
		return coachingUserDao.findByUser(user);
	}

	@Override
	public List<CoachingUser> findByCoaching_id(String id) {
		// TODO Auto-generated method stub
		return coachingUserDao.findByCoaching_id(id);
	}


	@Override
	public List<CoachingUser> findByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(
			String userid, String name, String userid2, String agency, Pageable pageable) {
		
		return coachingUserDao.findByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(userid, name, userid2, agency, pageable);
	}

	@Override
	public long countByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(
			String user, String name, String user2, String agency) {
		
		return coachingUserDao.countByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(user, name, user2, agency);
	}

	@Override
	public List<CoachingUser> findByUser_Id(String id) {
		return coachingUserDao.findByUser_Id(id);	
		}

	@Override
	public CoachingUser findByCoaching_IdAndUser_Id(String coachingId, String userId) {
		return coachingUserDao.findByCoaching_IdAndUser_Id(coachingId, userId);
	}

	@Override
	public boolean existsByCoaching_IdAndUser_Id(String coachingId, String userId) {
		return coachingUserDao.existsByCoaching_IdAndUser_Id(coachingId, userId);
	}

	
	
}
