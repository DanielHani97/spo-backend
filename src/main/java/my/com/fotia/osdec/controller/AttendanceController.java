package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class AttendanceController {
	
public static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);
	
	@Autowired
	AttendanceService attendanceService;

	@RequestMapping(value = "/attendance/all", method = RequestMethod.POST, produces = "application/json")
    public String attendanceList() throws Exception {
		
		String result = "";
		
		List<Attendance> ls = new ArrayList<Attendance>();
		ls = attendanceService.attendanceList();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		/*"meta": {
	        "page": 1,
	        "pages": 1,
	        "perpage": -1,
	        "total": 350,
	        "sort": "asc",
	        "field": "RecordID"
	    },*/
		
		result += "{";
		result += "\"meta\":{";
		result += "\"page\":"+1+",";
		result += "\"pages\":"+1+",";
		result += "\"perpage\":"+-1+",";
		result += "\"total\":"+10+",";
		result += "\"sort\":\"asc\""+",";
		result += "\"field\":\"id\"";
		result += "},";
		result += "\"data\":"+jsonRes+"}";
		
        return result;
    }
	
	@RequestMapping(value = "/attendancegetall", method = RequestMethod.GET)
	public ResponseEntity<List<Attendance>> trainingList() {
		List<Attendance> list = attendanceService.findAll();
		
		return new ResponseEntity<List<Attendance>>(list, HttpStatus.OK);
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(value = "/attendance/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getAttendance(@PathVariable("id") String id) {
//        logger.info("Fetching attendance with id {}", id);
//        Attendance attendance = attendanceService.findById(id);
//        if (attendance == null) {
//            logger.error("attendance with id {} not found.", id);
//            return new ResponseEntity(new CustomErrorType("Attendance id " + id 
//                    + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<Attendance>(attendance, HttpStatus.OK);
//    }
	
	@RequestMapping(value = "/attendance/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAttendance(@RequestBody Attendance attendance, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", attendance);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        attendance.setId(UUIDGenerator.getInstance().getId());
        
        attendance.setCreated_by(attendance.getCreated_by());
        attendance.setCreated_date(new Date());
        attendance.setModified_by(attendance.getModified_by());
        attendance.setModified_date(new Date());

        attendanceService.save(attendance);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(attendance.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/attendance/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAttendance(@PathVariable("id") String id, @RequestBody Attendance attendance) {
        logger.info("Updating attendance with id {}", id);
 
        Attendance currentAttendance = new Attendance();
        
        if (id !=null && !"".equals(id)) {
        	currentAttendance = attendanceService.findById(id);
        	
        	if (currentAttendance !=null) {
        		//currentAttendance.setImage(attendance.getImage());
        		currentAttendance.setStatus(attendance.getStatus());
        		currentAttendance.setRemarks(attendance.getRemarks());
        		currentAttendance.setDate(new Date());
  
        		attendanceService.save(currentAttendance);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Attendance with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Attendance with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Attendance>(currentAttendance, HttpStatus.OK);
    }
    
	@RequestMapping(value = "attendance/exist/{instanceId}", method = RequestMethod.POST)
	public boolean isExist(@PathVariable("instanceId") String instanceId, @RequestBody Attendance attendance) {
		boolean isExist = attendanceService.existsByInstanceIdAndDateAndUser_Id(instanceId, attendance.getDate(), attendance.getUser().getId());
		if(isExist) {
			return true;
		}else {
			return false;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/attendance/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAttendance(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Attendance with id {}", id);
 
        Attendance attendance = attendanceService.findById(id);
        if (attendance == null) {
            logger.error("Unable to delete. Attendance with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        attendanceService.deleteById(id);
        return new ResponseEntity<Attendance>(HttpStatus.NO_CONTENT);
    }

}
