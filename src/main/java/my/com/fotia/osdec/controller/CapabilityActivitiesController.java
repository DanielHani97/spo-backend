package my.com.fotia.osdec.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.capability.model.CapabilityActivities;
import my.com.fotia.osdec.capability.model.CapabilityUser;
import my.com.fotia.osdec.capability.service.CapabilityActivitiesService;
import my.com.fotia.osdec.capability.service.CapabilityCoachService;
import my.com.fotia.osdec.capability.service.CapabilityUserService;
import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;
import my.com.fotia.osdec.feedback.service.UserFeedbackTraxService;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CapabilityActivitiesController {
	
public static final Logger logger = LoggerFactory.getLogger(CapabilityActivitiesController.class);
	
	@Autowired
	CapabilityActivitiesService capabilityActivitiesService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	CapabilityUserService capabilityUserService;
	
	@Autowired
	CapabilityController capabilityController;
	
	@Autowired
	CapabilityCoachService capabilityCoachService;
	
	@Autowired
	UserFeedbackTraxService userFeedbackTraxService;
	
//	------------- USER CALENDAR ----------------
	@RequestMapping (value = "/capActivities/getuserAll/{id}", method = RequestMethod.POST, produces = "application/json" )
	public String capabList(@PathVariable("id") String id) throws Exception {
		
		String result = "";
		
		List <CapabilityUser> ls2 = capabilityUserService.findByUser_Id(id);
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); 
//	    df.setTimeZone(tz); 
	    
	    Iterator <CapabilityUser> iterator = ls2.iterator();
	    
	    result += "[";
	    
	    while(iterator.hasNext()) {
	    	String idCapabality = iterator.next().getCapability().getId();
	    	List<CapabilityActivities> ls = capabilityActivitiesService.findByCapability_Id(idCapabality);
	    	
	    	Iterator <CapabilityActivities> iterator2 = ls.iterator();
	    	
	    	while(iterator2.hasNext()) {
	    		
	    		CapabilityActivities obj = new CapabilityActivities();
	    	
		    	obj = iterator2.next();
		    	DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
			      
			      dateEnd = dateEnd.plusDays(1);
			      Date d2 = dateEnd.toDate();
		    			    			          
			        result += "{"; 
			        result += "\"allDay\": true,"; 
			        result += "\"type\": \"capability\","; 
			        result += "\"title\": "+"\""+obj.getCapability().getName()+"\",";
			        result += "\"name\": "+"\""+obj.getName()+"\",";
			        result += "\"id\": "+"\""+obj.getId()+"\",";         
			        result += "\"venue\": "+"\""+obj.getVenue()+"\","; 
			        result += "\"start\": "+"\""+obj.getStart_date()+"\",";
			        result += "\"endDate\": "+"\""+obj.getEnd_date()+"\",";
			        result += "\"end\": "+"\""+dateEnd+"\","; 
			        result += "\"kepakaran\": "+"\""+obj.getCapability().getKepakaran().getName()+"\",";
			        result += "\"editable\": false"; 
			        result += "},";         
		         
		      }             	    		    	
	    	
	    }
	    
	    result = removeLastChar(result); 
		result += "]"; 
		
		return result;
	}
	
//	-----------ADMIN CALENDAR------------
	
	@RequestMapping(value = "/capActivities/getall", method = RequestMethod.POST, produces = "application/json")
	public String capListing() throws Exception{
		
		String result ="";
		
		List <CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
		ls = capabilityActivitiesService.findAll();
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
//		df.setTimeZone(tz);
		
		Iterator<CapabilityActivities> iterator = ls.iterator();
		result += "[";
		
		while (iterator.hasNext()) {
			
			CapabilityActivities obj = new CapabilityActivities();
			
			obj = iterator.next();
			DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
		      
		      dateEnd = dateEnd.plusDays(1);
		      Date d2 = dateEnd.toDate();
		
			result += "{"; 
	        result += "\"allDay\": true,"; 
	        result += "\"type\": \"capability\","; 
	        result += "\"title\": "+"\""+obj.getCapability().getName()+"\",";
	        result += "\"name\": "+"\""+obj.getName()+"\",";
	        result += "\"id\": "+"\""+obj.getId()+"\",";         
	        result += "\"venue\": "+"\""+obj.getVenue()+"\","; 
	        result += "\"start\": "+"\""+obj.getStart_date()+"\",";
	        result += "\"endDate\": "+"\""+obj.getEnd_date()+"\",";
	        result += "\"end\": "+"\""+dateEnd+"\","; 
	        result += "\"kepakaran\": "+"\""+obj.getCapability().getKepakaran().getName()+"\",";
	        result += "\"editable\": false"; 
	        result += "},";    
			}
				
		result = removeLastChar(result);
		result += "]";
		
		return result;
	}
	
	@RequestMapping(value = "/capActivities/all/{id}/{id2}", method = RequestMethod.POST, produces = "application/json")
    public String capActivitiesList(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
		
		String result = "";
		
		List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
		ls = capabilityActivitiesService.findByCapability_IdAndAttendance(id, "Ada");
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);

		Iterator<CapabilityActivities> iterator = ls.iterator();
		
		result += "[";
		while (iterator.hasNext()) {

			CapabilityActivities obj = new CapabilityActivities();
			obj = iterator.next();
			
			DateTime dateStart = new DateTime(df.format(obj.getStart_date()));
			DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
			int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
			for (int i=0; i < days; i++) {
			    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
			    d = d.plusHours(8);

			    Date d2 = d.toDate();
			    List<Attendance> existAttendance = new ArrayList<Attendance>();
			    existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(id2, d2 , obj.getId());
			    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"title\": "+"\""+obj.getName()+"\",";
				result += "\"id\": "+"\""+obj.getId()+"\",";
				result += "\"attendance\": "+"\""+obj.getAttendance()+"\",";
				result += "\"description\": "+"\""+obj.getVenue()+"\",";
				result += "\"start\": "+"\""+d+"\",";
				if (existAttendance.isEmpty()) {
					result += "\"isExist\": false"+",";
					result += "\"className\": \"m-fc-event--solid-info m-fc-event--light\""+",";
				}else {
					result += "\"isExist\": true"+",";
					result += "\"className\": \"m-fc-event--solid-success m-fc-event--light\""+",";
				}
				result += "\"editable\": false";
				result += "},";
			}
		}
		result = removeLastChar(result);
		result += "]";
		
        return result;
    }
	
	// REPORT JSon2Table
	  @RequestMapping(value = "/capabilityActivity/report/{id}", method = RequestMethod.GET, produces = "application/json")
	    public String getReport(@PathVariable("id") String id) throws Exception {
	    
	    List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
	    ls = capabilityActivitiesService.findByCapability_IdAndAttendance(id, "Ada");
	    String result = "";
	    
	    TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	    df.setTimeZone(tz);    
	    
	    Iterator<CapabilityActivities> iterator = ls.iterator();
	    result += "[";
	    
	    while (iterator.hasNext()) {

	      CapabilityActivities obj = new CapabilityActivities();
	      obj = iterator.next();
	      
	      result += "{";
	      result += "\"title\": "+"\""+obj.getName()+"\",";
	      result += "\"id\": "+"\""+obj.getId()+"\",";
	      result += "\"laporan\": "+"[";
	      List<CapabilityUser> cu = capabilityUserService.findByCapability_idAndStatus(id, "3");
	      Iterator<CapabilityUser> iterate = cu.iterator();
	      while(iterate.hasNext()) {
	        CapabilityUser cUser = new CapabilityUser();
	        cUser = iterate.next();
	        
	          result += "{";
	          result += "\"jawatan\": "+"\""+cUser.getUser().getPosition()+"\",";
	          result += "\"peserta\": "+"\""+cUser.getUser().getName()+"\",";
	          result += "\"agensi\": "+"\""+cUser.getUser().getAgency().getCode()+"\",";
	        
	          DateTime dateStart = new DateTime(df.format(obj.getStart_date()));
	          DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
	          result += "\"report\": "+"[";
	          int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
	          for (int i=0; i < days; i++) {
	            DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
	            
	            d = d.plusHours(8);
	            Date d2 = d.toDate();
	            
	            result += "{";
	            
	            Calendar myDate = Calendar.getInstance();
	            myDate.setTime(d2);
	            int dow = myDate.get (Calendar.DAY_OF_WEEK);
	            
	            String day="";
	            
	            if(dow == Calendar.MONDAY) {
	              day = "Isnin";
	            }else if(dow == Calendar.TUESDAY) {
	              day = "Selasa";
	            }else if(dow == Calendar.WEDNESDAY) {
	              day = "Rabu";
	            }else if(dow == Calendar.THURSDAY) {
	              day = "Khamis";
	            }else if(dow == Calendar.FRIDAY) {
	              day = "Jumaat";
	            }else if(dow == Calendar.SATURDAY) {
	              day = "Sabtu";
	            }else if(dow == Calendar.SUNDAY) {
	              day = "Ahad";
	            }
	            
	            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
	            String strDate = dt1.format(d2);
	            
	          result += "\"date\": "+"\""+strDate+" ("+day+") "+"\",";
	          List<Attendance> existAttendance = new ArrayList<Attendance>();
	          existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(cUser.getUser().getId(), d2 , obj.getId());
	          if(existAttendance.isEmpty()) {
	            result += "\"kehadiran\": \"Tidak Hadir\"";
	          }else {
	            result += "\"kehadiran\": \"Hadir\"";
	          }
	          result += "},";
	          
	          
	        }
	        result = removeLastChar(result);
	        result += "]";
	          result += "},";
	        
	      }
	      result = removeLastChar(result);
	      result += "]";
	      result += "},";
	    }
	    result = removeLastChar(result);
	    result += "]";
	    
	    
	        return result;
	    }
	  
	@RequestMapping(value = "/capActivities2/all/{id}/{id2}", method = RequestMethod.POST, produces = "application/json")
    public String capActivitiesList2(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
		
		String result = "";
		
		List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
		ls = capabilityActivitiesService.findByCapability_IdAndAttendance(id, "Tiada");
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);

		Iterator<CapabilityActivities> iterator = ls.iterator();
		
		result += "[";
		while (iterator.hasNext()) {

			CapabilityActivities obj = new CapabilityActivities();
			obj = iterator.next();
			
			DateTime dateStart = new DateTime(df.format(obj.getStart_date()));
			DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
			int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
			for (int i=0; i < days; i++) {
			    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
			    d = d.plusHours(8);

			    Date d2 = d.toDate();
			    List<Attendance> existAttendance = new ArrayList<Attendance>();
			    existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(id2, d2 , obj.getId());
			    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"title\": "+"\""+obj.getName()+"\",";
				result += "\"id\": "+"\""+obj.getId()+"\",";
				result += "\"attendance\": "+"\""+obj.getAttendance()+"\",";
				result += "\"description\": "+"\""+obj.getVenue()+"\",";
				result += "\"start\": "+"\""+d+"\",";
				if (existAttendance.isEmpty()) {
					result += "\"isExist\": false"+",";
					result += "\"className\": \"m-fc-event--solid-danger m-fc-event--light\""+",";
				}else {
					result += "\"isExist\": true"+",";
					result += "\"className\": \"m-fc-event--solid-warning m-fc-event--light\""+",";
				}
				result += "\"editable\": false";
				result += "},";
			}
		}
		result = removeLastChar(result);
		result += "]";
		
        return result;
    }
	
	@RequestMapping(value = "/capabilityActivities/all", method = RequestMethod.POST, produces = "application/json")
    public String capabilityActivitiesList() throws Exception {
		
		String result = "";
		
		List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
		ls = capabilityActivitiesService.capabilityActivitiesList();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		result += jsonRes;
		
        return result;
    }
	
	@RequestMapping(value = "/capabilityActivities", method = RequestMethod.GET, produces = "application/json")
    public String getAcr() throws Exception {
		
		List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
		ls = capabilityActivitiesService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityActivities/cap/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<CapabilityActivities>> getCapabilityActivitiesByCapId(@PathVariable("id") String id) {
        logger.info("Fetching capabilityActivities with id {}", id);
        List<CapabilityActivities> ls = new ArrayList<CapabilityActivities>();
        ls = capabilityActivitiesService.findByCapability_Id(id);
        if (ls.isEmpty()) {
            logger.error("capabilityActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<CapabilityActivities>>(ls, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityActivities/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCapabilityActivities(@PathVariable("id") String id) {
        logger.info("Fetching capabilityActivities with id {}", id);
        CapabilityActivities capabilityActivities = capabilityActivitiesService.findById(id);
        if (capabilityActivities == null) {
            logger.error("capabilityActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CapabilityActivities>(capabilityActivities, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/capabilityActivities/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCapabilityActivities(@RequestBody CapabilityActivities capabilityActivities, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", capabilityActivities);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        capabilityActivities.setId(UUIDGenerator.getInstance().getId());
        
        capabilityActivitiesService.save(capabilityActivities);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(capabilityActivities.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capabilityActivities/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> keygenAktiviti(@PathVariable("id") String id, @RequestBody CapabilityActivities aktiviti) {
        logger.info("Updating aktiviti with id {}", id);
        
        CapabilityActivities current = new CapabilityActivities();
        
        
        if(id !=null && !"".equals(id)) {
        	current = capabilityActivitiesService.findById(id);
            
            if(current != null) {
            	current.setKeygen(aktiviti.getKeygen());
            	
            	capabilityActivitiesService.save(current);
            	
            }else {
            	 logger.error("Unable to update. aktiviti with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. aktiviti with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        capabilityActivitiesService.save(current);
        return new ResponseEntity<CapabilityActivities>(current, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capabilityActivities/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCapabilityActivities(@RequestBody String json) {
		
		System.out.println(json);
		
        
        return new ResponseEntity<CapabilityActivities>(HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityActivities/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCapabilityActivities(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting CapabilityActivities with id {}", id);
 
        CapabilityActivities capabilityActivities = capabilityActivitiesService.findById(id);
        if (capabilityActivities == null) {
            logger.error("Unable to delete. capabilityActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        capabilityActivitiesService.deleteById(id);
        return new ResponseEntity<CapabilityActivities>(HttpStatus.NO_CONTENT);
    }
	
	
	public String removeLastChar(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityActivities/list/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getXtvtFb(@PathVariable("id") String id) {
		logger.info("Fetching capabilityActivities with id {}", id);
		
        List<CapabilityActivities> ls = capabilityActivitiesService.findByCapability_Id(id);
        
        if (ls.size() > 0) {
        	
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		JwtUser currentUser = (JwtUser) auth.getPrincipal();
    		String currentUserId = currentUser.getId();
    		
    		String role = capabilityController.getUserRole(id, currentUserId);
    		
            for(CapabilityActivities obj: ls) {
            	
            	List<User> partipantLs = new ArrayList<User>();
            	
            	String activityid = obj.getId();
            	
            	if("ROLE_COACH".equals(role)) {
            		List<CapabilityUser> userLs = capabilityUserService.findByCapability_id(id);
            		for(CapabilityUser cu: userLs) {
            			
            			User cuObj  = cu.getUser();
            			User newObj = new User();
                		String userid = cu.getUser().getId();
                		String txId = "";
                		
                		boolean isFbExist = false;
                		
                		List<UserFeedbackTrax> lsTrax = userFeedbackTraxService.findByParentidAndInstanceidAndUser_IdAndType
                    			(id, activityid, userid, "CAPABILITY");
            			
            			for(UserFeedbackTrax trax: lsTrax) {
            				String trxUserId = trax.getUser().getId();
            				String trxCreatedId = trax.getCreatedby().getId();
            				
            				if(!trxUserId.equals(trxCreatedId)) {
            					isFbExist = true;
            					txId = trax.getId();
            				}
            			}
                		
                		newObj.setId(cuObj.getId());
                		newObj.setName(cuObj.getName());
                		newObj.setEmail(cuObj.getEmail());
                		newObj.setPosition(cuObj.getPosition());
                		newObj.setEnabled(isFbExist);
                		if(isFbExist) {
                			newObj.setType(txId);//temp id
                		}
                		
                		partipantLs.add(newObj);
                	}
            		obj.setUserLs(partipantLs);
            	}else if("ROLE_USER".equals(role)) {
            		
            		boolean isFbExist = false;
            		String txId = "";
            		
            		List<UserFeedbackTrax> lsTrax = userFeedbackTraxService.findByParentidAndInstanceidAndUser_IdAndType
                			(id, activityid, currentUserId, "CAPABILITY");
        			
        			for(UserFeedbackTrax trax: lsTrax) {
        				String trxUserId = trax.getUser().getId();
        				String trxCreatedId = trax.getCreatedby().getId();
        				
        				if(trxUserId.equals(trxCreatedId)) {
        					isFbExist = true;
        					txId = trax.getId();
        				}
        			}
        			
                	obj.setDone(isFbExist);
                	obj.setKeygen(txId);//temp keep traxId
            	}
            }
            
        }else {
        	logger.error("capabilityActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("capabilityActivities with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<List<CapabilityActivities>>(ls, HttpStatus.OK);
    }

}
