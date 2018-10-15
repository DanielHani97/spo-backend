package my.com.fotia.osdec.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.user.dao.AuthorityDao;
import my.com.fotia.osdec.user.model.Authority;

@Service
public class AuthorityServiceImpl implements AuthorityService{

	@Autowired
	AuthorityDao authorityDao;
	
	@Override
	public List<Authority> findAll() {
		return authorityDao.findAll();
	}

	@Override
	public Authority findById(String id) {
		return authorityDao.findById(id);
	}

	@Override
	public Authority findByName(String name) {
		return authorityDao.findByName(name);
	}

}
