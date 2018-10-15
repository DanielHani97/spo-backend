package my.com.fotia.osdec.attendance.service;

import java.util.Date;
import java.util.List;

import my.com.fotia.osdec.attendance.model.Attendance;

public interface AttendanceService {

	Attendance findById(String id);
	Attendance findByInstanceId (String instanceId);
	List<Attendance> findAll();
	List<Attendance> attendanceList();
	void save(Attendance attendance);
	void deleteById(String id);
	
	List<Attendance> findByUser_IdAndDateAndInstanceId(String userid, Date d2, String instanceId );
	Attendance findByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id);
	boolean existsByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id);
	
}
