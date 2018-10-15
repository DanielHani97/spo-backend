package my.com.fotia.osdec.attendance.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.attendance.dao.AttendanceDao;
import my.com.fotia.osdec.attendance.model.Attendance;

@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	@Autowired
	AttendanceDao attendanceDao;

	public Attendance findById(String id) {
		return attendanceDao.findById(id);
	}
	
	public Attendance findByInstanceId(String instanceId) {
		return attendanceDao.findByInstanceId(instanceId);
	}
	
	@Autowired
	public List<Attendance> findAll() {
		return attendanceDao.findAll();
	}

	public List<Attendance> attendanceList() {
		return attendanceDao.findAll();
	}

	@Override
	public void save(Attendance attendance) {
		attendanceDao.save(attendance);
	}

	@Override
	public void deleteById(String id) {
		attendanceDao.deleteById(id);
	}

	@Override
	public List<Attendance> findByUser_IdAndDateAndInstanceId(String userid, Date string, String instanceId) {
		
		return attendanceDao.findByUser_IdAndDateAndInstanceId(userid, string, instanceId);
	}

	@Override
	public Attendance findByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id) {
		return attendanceDao.findByInstanceIdAndDateAndUser_Id(instanceId, date, id);
	}

	@Override
	public boolean existsByInstanceIdAndDateAndUser_Id(String instanceId, Date date, String id) {
		return attendanceDao.existsByInstanceIdAndDateAndUser_Id(instanceId, date, id);
	}

	

}
