package my.com.fotia.osdec.general.agency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.agency.dao.CompanyDao;
import my.com.fotia.osdec.general.agency.model.Company;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyDao companyDao;
	
	@Override
	public Company findByName(String name) {
		return companyDao.findByName(name);
	}

	@Override
	public Company findById(String id) {
		return companyDao.findById(id);
	}

	@Override
	public List<Company> findAll() {
		return companyDao.findAll();
	}

	@Override
	public void save(Company agency) {
		companyDao.save(agency);
	}

	@Override
	public void deleteById(String id) {
		companyDao.deleteById(id);
	}

	@Override
	public List<Company> findByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(
			String name, String phoneNo, String state, String city, Pageable pageable) {
		return companyDao.findByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(name, phoneNo, state, city, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(
			String name, String phoneNo, String state, String city) {
		return companyDao.countByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(name, phoneNo, state, city);
	}

}
