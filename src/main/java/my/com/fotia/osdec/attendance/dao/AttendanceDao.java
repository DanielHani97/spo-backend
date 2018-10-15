package my.com.fotia.osdec.attendance.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.attendance.model.Attendance;

@Transactional
public interface AttendanceDao extends CrudRepository<Attendance, Long>{
	
	Attendance findById(String id);
	Attendance findByInstanceId (String instanceId);
	List<Attendance> findAll();
	
	@Modifying
	@Query("DELETE from Attendance where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Attendance> findByUser_IdAndDateAndInstanceId(String userid, Date string, String instanceId );
	Attendance findByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id);
	boolean existsByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id);
}
