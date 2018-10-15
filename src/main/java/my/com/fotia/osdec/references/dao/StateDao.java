package my.com.fotia.osdec.references.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.com.fotia.osdec.references.model.State;

public interface StateDao extends JpaRepository<State, Long>{
	
	List<State> findAll();
	State findById(String id);
}
