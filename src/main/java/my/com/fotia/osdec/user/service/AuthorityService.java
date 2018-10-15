package my.com.fotia.osdec.user.service;

import java.util.List;

import my.com.fotia.osdec.user.model.Authority;

public interface AuthorityService {

	Authority findById(String id);
	Authority findByName(String name);
	List<Authority> findAll();
}
