package my.com.fotia.osdec.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
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

import my.com.fotia.osdec.capability.model.Capability;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;
import my.com.fotia.osdec.coaching.activities.service.CoachingActivitiesService;
import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.GlobalFunction;
import my.com.fotia.osdec.utilities.UUIDGenerator;
import my.com.fotia.osdec.coaching.user.service.CoachingUserService;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;
import my.com.fotia.osdec.feedback.service.UserFeedbackTraxService;
import my.com.fotia.osdec.general.manday.transaction.model.MandayTransaction;
import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.general.manday.transaction.service.MandayTransactionService;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.user.model.User;



@RestController
@RequestMapping("/api")
public class CoachingActivitiesController {
	
public static final Logger logger = LoggerFactory.getLogger(CoachingActivitiesController.class);
	
	@Autowired
	CoachingActivitiesService coachingActivitiesService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	MandayTransactionService mandayTransactionService;
	
	@Autowired
	CoachingUserService coachingUserService;
	
	@Autowired
	UserFeedbackTraxService userFeedbackTraxService;
	
	@Autowired
	CoachingController coachingController;
	
	//	-----------USER CALENDAR------------
	@RequestMapping(value = "/coachingActivities/getuserAll/{id}", method = RequestMethod.POST , produces = "application/json" )
	public String coachList(@PathVariable("id") String id) throws Exception {
		
		String result = "";
		List<CoachingUser> ls2 = coachingUserService.findByUser_Id(id);
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); 
//	    df.setTimeZone(tz); 
	    
	    Iterator <CoachingUser> iterator = ls2.iterator();
	    
	   result += "[";
	    
	   while(iterator.hasNext()) {
		   String idCoaching = iterator.next().getCoaching().getId();
		   List<CoachingActivities> ls = coachingActivitiesService.findByCoaching_IdAndKelulusan(idCoaching, "2");
		   		   		   
	    	Iterator <CoachingActivities> iterator2 = ls.iterator();
		   	    		    
		    while (iterator2.hasNext()) {
		    	CoachingActivities obj = new CoachingActivities(); 
		    	
		    	obj = iterator2.next();
	
		    	DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
			      
			      dateEnd = dateEnd.plusDays(1);
			      Date d2 = dateEnd.toDate();
		    			    				          
			        result += "{"; 
			        result += "\"allDay\": true,";
			        result += "\"type\": \"coaching\","; 
			        result += "\"title\": "+"\""+obj.getCoaching().getName()+"\",";
			        result += "\"name\": "+"\""+obj.getName()+"\",";
			        result += "\"id\": "+"\""+obj.getId()+"\",";         
			        result += "\"venue\": "+"\""+obj.getVenue()+"\","; 
			        result += "\"start\": "+"\""+obj.getStart_date()+"\",";
			        result += "\"endDate\": "+"\""+obj.getEnd_date()+"\",";
			        result += "\"end\": "+"\""+dateEnd+"\",";  
			        result += "\"backend\": "+"\""+obj.getCoaching().getBackend().getName()+"\",";
			        result += "\"frontend\": "+"\""+obj.getCoaching().getFrontend().getName()+"\",";
			        result += "\"database\": "+"\""+obj.getCoaching().getDatabase().getName()+"\",";
			        result += "\"editable\": false"; 
			        result += "},";         
			         
			      }             	    
		    
	    
	    }
	   result = removeLastChar(result); 
	   result += "]"; 
		
		return result;
		
	}
	
	
	//-----------ADMIN CALENDAR------------
	@RequestMapping(value = "/coachingActivities/getall", method = RequestMethod.POST , produces = "application/json") 
	  public String coachingListing() throws Exception{ 
	     
	    String result = ""; 
	     
	    List <CoachingActivities> ls = new ArrayList <CoachingActivities>(); 
	    ls = coachingActivitiesService.findAll(); 
	     
//	    TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); 
//	    df.setTimeZone(tz); 
	     
	    Iterator <CoachingActivities> iterator = ls.iterator(); 
	     
	    result += "["; 
	     
	    while (iterator.hasNext()) { 
	 
	      CoachingActivities obj = new CoachingActivities(); 
	      
	       
	      obj = iterator.next(); 
	      
	      DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
	      
	      dateEnd = dateEnd.plusDays(1);
	      Date d2 = dateEnd.toDate();
	     	    
	       	      	          
	        result += "{"; 
	        result += "\"allDay\": true,"; 
	        result += "\"type\": \"coaching\","; 
	        result += "\"title\": "+"\""+obj.getCoaching().getName()+"\",";
	        result += "\"name\": "+"\""+obj.getName()+"\","; 
	        result += "\"id\": "+"\""+obj.getId()+"\",";         
	        result += "\"venue\": "+"\""+obj.getVenue()+"\","; 
	        result += "\"start\": "+"\""+obj.getStart_date()+"\",";
	        result += "\"endDate\": "+"\""+obj.getEnd_date()+"\",";
	        result += "\"end\": "+"\""+dateEnd+"\",";  
	        result += "\"backend\": "+"\""+obj.getCoaching().getBackend().getName()+"\",";
	        result += "\"frontend\": "+"\""+obj.getCoaching().getFrontend().getName()+"\",";
	        result += "\"database\": "+"\""+obj.getCoaching().getDatabase().getName()+"\",";
	        result += "\"editable\": false"; 
	        result += "},";         
	         
	      }             
	 
	     
	    result = removeLastChar(result); 
	    result += "]"; 
	         
	        return result; 
	  } 

	//-----------ATTENDANCE COACH isEXiST
	@RequestMapping(value = "/coachingAttendanceCoach/{id}", method = RequestMethod.GET, produces = "application/json")
    public String coachingCoachAttendance(@PathVariable("id") String id) throws Exception {
		
		
		
		String result = "";
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_IdAndAttendanceAndKelulusan(id, "Ada", "2");
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
	
		Iterator<CoachingActivities> iterator = ls.iterator();
		result += "[";
		
		while (iterator.hasNext()) {

			CoachingActivities obj = new CoachingActivities();
			obj = iterator.next();
			
			DateTime dateStart = new DateTime(df.format(obj.getStart_date()));
			DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));

			int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
			for (int i=0; i < days; i++) {
			    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
			    d = d.plusHours(8);

			    Date d2 = d.toDate();
			   
			    List<MandayTransaction> existTransaction = new ArrayList<MandayTransaction>();
			    existTransaction = mandayTransactionService.findByInstanceIdAndInstanceDate(obj.getId(), d2);

			    result += "{";
				result += "\"allDay\": true,";
				result += "\"title\": "+"\""+obj.getName()+"\",";
				result += "\"id\": "+"\""+obj.getId()+"\",";
				result += "\"attendance\": "+"\""+obj.getAttendance()+"\",";
				result += "\"description\": "+"\""+obj.getVenue()+"\",";
				result += "\"start\": "+"\""+d+"\",";
				if (existTransaction.isEmpty()) {
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
	
	@RequestMapping(value = "/coachingActivities/all/{id}/{id2}", method = RequestMethod.GET, produces = "application/json")
    public String coachingActivitiesList(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
		
		String result = "";
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_IdAndAttendanceAndKelulusan(id, "Ada", "2");
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
	
		
		
		Iterator<CoachingActivities> iterator = ls.iterator();
		result += "[";

		while (iterator.hasNext()) {

			CoachingActivities obj = new CoachingActivities();
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
			    //dates.add(d);
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
	
	@RequestMapping(value = "/coachingActivities/all2/{id}/{id2}", method = RequestMethod.GET, produces = "application/json")
    public String coachingActivitiesListNoAttendance(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
		
		String result = "";
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_IdAndAttendanceAndKelulusan(id, "Tiada", "2");
				
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
			
		
		Iterator<CoachingActivities> iterator = ls.iterator();
		result += "[";
		//int count = 0;
		while (iterator.hasNext()) {

			CoachingActivities obj = new CoachingActivities();
			obj = iterator.next();
			
			DateTime dateStart = new DateTime(df.format(obj.getStart_date()));
			DateTime dateEnd = new DateTime(df.format(obj.getEnd_date()));
			//List<DateTime> dates = new ArrayList<DateTime>();
			Interval interval = new Interval(dateStart, dateEnd);
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
	
	@RequestMapping(value = "/coachingAct/getall/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getAcr(@PathVariable("id") String id) throws Exception {
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_Id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	// REPORT JSon2Table
	@RequestMapping(value = "/coachingActivity/report/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getActivity(@PathVariable("id") String id) throws Exception {
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_IdAndAttendanceAndKelulusan(id, "Ada" , "2");
		String result = "";
		
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);		
		
		Iterator<CoachingActivities> iterator = ls.iterator();
		result += "[";
		
		while (iterator.hasNext()) {

			CoachingActivities obj = new CoachingActivities();
			obj = iterator.next();
			
			result += "{";
			result += "\"title\": "+"\""+obj.getName()+"\",";
			result += "\"id\": "+"\""+obj.getId()+"\",";
			result += "\"laporan\": "+"[";
			List<CoachingUser> cu = coachingUserService.findByCoaching_id(id);
			Iterator<CoachingUser> iterate = cu.iterator();
			while(iterate.hasNext()) {
				CoachingUser cUser = new CoachingUser();
				cUser = iterate.next();
				
			    result += "{";
			    result += "\"jawatan\": "+"\""+cUser.getUser().getPosition()+"\",";
			    result += "\"peserta\": "+"\""+cUser.getUser().getName()+"\",";
			    String agensi = "";
			    if(cUser.getUser().getType().equals("GOV")) {
			    	agensi = cUser.getUser().getAgency().getCode();
			    }else {
			    	agensi = cUser.getUser().getCompany().getName();
			    }
			    result += "\"agensi\": "+"\""+agensi+"\",";
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
	
	@RequestMapping(value = "/coachingAct/getall/new/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getNewAct(@PathVariable("id") String id) throws Exception {
		
		List<CoachingActivities> ls = new ArrayList<CoachingActivities>();
		ls = coachingActivitiesService.findByCoaching_IdAndKelulusan(id, "1");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingActivities/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoachingActivities(@PathVariable("id") String id) {
        logger.info("Fetching coachingActivities with id {}", id);
        CoachingActivities coachingActivities = coachingActivitiesService.findById(id);
        if (coachingActivities == null) {
            logger.error("coachingActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CoachingActivities>(coachingActivities, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/coachingActivities/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCoachingActivities(@RequestBody CoachingActivities coachingActivities, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", coachingActivities);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        
        coachingActivities.setId(UUIDGenerator.getInstance().getId());
        
        Date startDate = GlobalFunction.removeTime(coachingActivities.getStart_date());
        coachingActivities.setStart_date(startDate);
        
        Date endDate = GlobalFunction.removeTime(coachingActivities.getEnd_date());
        coachingActivities.setEnd_date(endDate);
        
        coachingActivitiesService.save(coachingActivities);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(coachingActivities.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coachingActivities/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCoachingActivities(@RequestBody String json) {
		
		System.out.println(json);
		
        
        return new ResponseEntity<CoachingActivities>(HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coachingActivities/lulus/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> lulusAktiviti(@PathVariable("id") String id, @RequestBody CoachingActivities aktiviti) {
        logger.info("Updating aktiviti with id {}", id);
        
        CoachingActivities current = new CoachingActivities();
        
        
        if(id !=null && !"".equals(id)) {
        	current = coachingActivitiesService.findById(id);
            
            if(current != null) {
            	current.setKelulusan(aktiviti.getKelulusan());
            	
            	coachingActivitiesService.save(current);
            	
            }else {
            	 logger.error("Unable to update. aktiviti with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. aktiviti with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        coachingActivitiesService.save(current);
        return new ResponseEntity<CoachingActivities>(current, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coachingActivities/keygen/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> keygenAktiviti(@PathVariable("id") String id, @RequestBody CoachingActivities aktiviti) {
        logger.info("Updating aktiviti with id {}", id);
        
        CoachingActivities current = new CoachingActivities();
        
        
        if(id !=null && !"".equals(id)) {
        	current = coachingActivitiesService.findById(id);
            
            if(current != null) {
            	
            	coachingActivitiesService.save(current);
            	
            }else {
            	 logger.error("Unable to update. aktiviti with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. aktiviti with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        coachingActivitiesService.save(current);
        return new ResponseEntity<CoachingActivities>(current, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingActivities/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoachingActivities(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting CoachingActivities with id {}", id);
 
        CoachingActivities coachingActivities = coachingActivitiesService.findById(id);
        if (coachingActivities == null) {
            logger.error("Unable to delete. coachingActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        coachingActivitiesService.deleteById(id);
        return new ResponseEntity<CoachingActivities>(HttpStatus.NO_CONTENT);
    }
	
	public String removeLastChar(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingActivities/list/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getXtvtFb(@PathVariable("id") String id) {
		logger.info("Fetching coachingActivities with id {}", id);
		
        List<CoachingActivities> ls = coachingActivitiesService.findByCoaching_Id(id);
        
        if (ls.size() > 0) {
        	
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		JwtUser currentUser = (JwtUser) auth.getPrincipal();
    		String currentUserId = currentUser.getId();
    		
    		String role = coachingController.getUserRole(id, currentUserId);
    		
            for(CoachingActivities obj: ls) {
            	
            	List<User> partipantLs = new ArrayList<User>();
            	
            	String activityid = obj.getId();
            	
            	if("ROLE_COACH".equals(role)) {
            		List<CoachingUser> userLs = coachingUserService.findByCoaching_id(id);
            		for(CoachingUser cu: userLs) {
            			
            			User cuObj  = cu.getUser();
            			User newObj = new User();
                		String userid = cu.getUser().getId();
                		String txId = "";
                		
                		boolean isFbExist = false;
                		
                		List<UserFeedbackTrax> lsTrax = userFeedbackTraxService.findByParentidAndInstanceidAndUser_IdAndType
                    			(id, activityid, userid, "COACHING");
                		
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
                			(id, activityid, currentUserId, "COACHING");
        			
        			for(UserFeedbackTrax trax: lsTrax) {
        				String trxUserId = trax.getUser().getId();
        				String trxCreatedId = trax.getCreatedby().getId();
        				
        				if(trxUserId.equals(trxCreatedId)) {
        					isFbExist = true;
        					txId = trax.getId();
        				}
        			}
        			
                	obj.setDone(isFbExist);
                	obj.setInstanceId(txId);//temp keep traxId
            	}
            }
            
        }else {
        	logger.error("coachingActivities with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("coachingActivities with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<List<CoachingActivities>>(ls, HttpStatus.OK);
    }


}
