package my.com.fotia.osdec.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.user.dao.UserDao;
import my.com.fotia.osdec.user.model.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;

	public User findByName(String name) {
		return userDao.findByName(name);
	}

	public User findById(String id) {
		return userDao.findById(id);
	}

	public List<User> userList() {
		return userDao.findAll();
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userDao.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public List<User> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase(
			String name, String username, String email, String agencyName, String position, Pageable pageable) {
		return userDao.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase(name, username, email, agencyName, position, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase(
			String name, String username, String email, String agencyName, String position) {
		return userDao.countByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase(name, username, email, agencyName, position);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void updateStatus(boolean status, String id) {
		userDao.updateStatus(status, id);
	}

	@Override
	public List<User> findByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled(
			String name, boolean status1, String username, boolean status2, String email, boolean status3,
			String agencyName, boolean status, Pageable pageable) {
		return userDao.findByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled(name, status1, username, status2, email, status3, agencyName, status, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled(
			String name, boolean status1, String username, boolean status2, String email, boolean status3,
			String agencyName, boolean status4) {
		return userDao.countByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled(name, status1, username, status2, email, status3, agencyName, status4);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
}
