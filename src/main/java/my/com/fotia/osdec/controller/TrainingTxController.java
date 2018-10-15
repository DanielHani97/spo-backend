package my.com.fotia.osdec.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Collection; 

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.feedback.model.UserFeedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;
import my.com.fotia.osdec.feedback.service.UserFeedbackService;
import my.com.fotia.osdec.feedback.service.UserFeedbackTraxService;
import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.training.service.TrainingService;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.trainingTx.service.TrainingTxService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class TrainingTxController {
	
public static final Logger logger = LoggerFactory.getLogger(TrainingTxController.class);
	
	@Autowired
	TrainingTxService trainingTxService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TrainingService trainingService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	TrainingCoachController trainingCoachController;
	
	@Autowired
	UserFeedbackTraxService userFeedbackTraxService;
	
	@Autowired
	UserFeedbackService userFeedbackService;
	
	 // AUDIT TRAINING 
	  @RequestMapping(value = "/trainingTxAudit", method = RequestMethod.POST, produces = "application/json") 
	    public String trainingAuditList( 
	        @RequestParam ("datatable[pagination][page]") int page, 
	        @RequestParam ("datatable[pagination][perpage]") int perpage, 
	        @RequestParam ("datatable[sort][sort]") String sort, 
	        @RequestParam ("datatable[sort][field]") String sortField, 
	        @RequestParam (value= "datatable[search]", required=false) String search 
	        ) throws Exception { 
	     
	    String result = ""; 
	    int pages = 1; 
	     
	    List<TrainingTx> ls = new ArrayList<TrainingTx>(); 
	     
	    try { 
	       
	      //calculate page number and number of record perpage 
	      int limit = perpage; 
	      int offset = page * perpage - perpage ; 
	       
	      //set direction and sorting 
	      Direction sortDirection = Direction.ASC; 
	      if(sort.toUpperCase().equals("DESC")) { 
	        sortDirection = Direction.DESC; 
	      } 
	       
	      if(search == null || search.equals(null)) { 
	        search = ""; 
	      } 
	      
	      if(sortField.equals("title")) {
				sortField = "Training_Title";	
	      }else if(sortField.equals("name")) {
				sortField = "User_Name";	
	      }
	       
	      Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "3", "4", "5" })); 
	      Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField)); 
	      Pageable pageable = new PageRequest(page-1, perpage, sortObj); 
	 
	      ls = trainingTxService.findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase 
	          (collection, search, collection, search, collection, search, pageable); 
	 
	      ObjectMapper mapper = new ObjectMapper(); 
	      String jsonRes = mapper.writeValueAsString(ls); 
	       
	      Long totalL = trainingTxService.countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase 
	          (collection, search, collection, search, collection, search); 
	           
	      int total = 0; 
	      double perpageDb = 0.0; 
	       
	      try { 
	        total = totalL.intValue(); 
	        perpageDb = (double) perpage; 
	      }catch(Exception e) {} 
	       
	      pages = (int) Math.ceil(total / perpageDb); 
	       
	      result += "{"; 
	      result += "\"meta\":{"; 
	      result += "\"page\":"+page+","; 
	      result += "\"pages\":"+pages+","; 
	      result += "\"perpage\":"+perpage+","; 
	      result += "\"total\":"+total+","; 
	      result += "\"sort\":\""+sort+"\","; 
	      result += "\"field\":\"id\""; 
	      result += "},"; 
	      result += "\"data\":"+jsonRes+"}"; 
	       
	    } catch (Exception e) { 
	      logger.error(e.getMessage()); 
	    } 
	     
	        return result; 
	    } 
	  
	
	
	@RequestMapping(value = "/trainingTx/getall/{id}", method = RequestMethod.POST, produces = "application/json")
    public String trainTranscList(@PathVariable("id") String id) throws Exception {
		
		String result = "";
		
		TrainingTx ls = new TrainingTx();
		ls = trainingTxService.findById(id);
						
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		
		
		result += "[";
					
			TrainingTx obj = new TrainingTx();
			Training train = new Training();
			obj = ls;
			train = obj.getTraining();
			DateTime dateStart = new DateTime(df.format(train.getStartDate()));
			DateTime dateEnd = new DateTime(df.format(train.getEndDate()));
			
			int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
			for (int i=0; i < days; i++) {
			    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
			    d = d.plusHours(8);

			    Date d2 = d.toDate();
			    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"title\": "+"\""+train.getTitle()+"\",";
				result += "\"id\": "+"\""+obj.getId()+"\",";				
				result += "\"description\": "+"\""+train.getPlace()+"\",";
				result += "\"start\": "+"\""+d+"\",";
				result += "\"textColor\": \"#000000\""+",";
				result += "\"color\": \"#FFFFFF\""+",";
				result += "\"editable\": false";
				result += "},";		

		}
		result = removeLastChar(result);
		result += "]";
		
		
        return result;
    }
	
	@RequestMapping(value = "/trainingTxall", method = RequestMethod.GET)
	public ResponseEntity<List<TrainingTx>> trainingTxLs() {
		
		List<TrainingTx> ls = new ArrayList<TrainingTx>();
		ls = trainingTxService.findAll();
		
		if(ls.isEmpty()){
            return new ResponseEntity<List<TrainingTx>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<TrainingTx>>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/trainingTx/all/{id}/{id2}", method = RequestMethod.POST, produces = "application/json")
    public String trainingTranscList(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
		
		String result = "";
		
		TrainingTx ls = new TrainingTx();
		ls = trainingTxService.findById(id);
						
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		df.setTimeZone(tz);
		
		
		result += "[";
					
			TrainingTx obj = new TrainingTx();
			Training train = new Training();
			obj = ls;
			train = obj.getTraining();
			DateTime dateStart = new DateTime(df.format(train.getStartDate()));
			DateTime dateEnd = new DateTime(df.format(train.getEndDate()));
			
			int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
			for (int i=0; i < days; i++) {
			    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
			    d = d.plusHours(8);

			    Date d2 = d.toDate();
			    List<Attendance> existAttendance = new ArrayList<Attendance>();
			    existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(id2, d2 , obj.getId());
			    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"title\": "+"\""+train.getTitle()+"\",";
				result += "\"id\": "+"\""+obj.getId()+"\",";	
				result += "\"description\": "+"\""+train.getPlace()+"\",";
				result += "\"start\": "+"\""+d+"\",";	
				if (existAttendance.isEmpty()) {
					result += "\"isExist\": false"+",";
					result += "\"borderColor\": \"#716ACA\""+",";
					result += "\"className\": \"m-fc-event--solid-info m-fc-event--light\""+",";
				}else {
					result += "\"isExist\": true"+",";
					result += "\"borderColor\": \"#34BFA3\""+",";
			        result += "\"className\": \"m-fc-event--solid-success m-fc-event--light\""+","; 
				}
				//result += "\"keygen\": "+"\""+ train.getKeygen()+"\",";	
				result += "\"editable\": false";
				result += "},";		

		}
		result = removeLastChar(result);
		result += "]";
		
		
        return result;
    }

	private TrainingTx getTraining() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/all", method = RequestMethod.POST)
    public  String getTrainingTxList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;
		
		//User user = userService.findById(userid);
		
		List<TrainingTx> ls = new ArrayList<TrainingTx>();
		
		try {
			
			//calculate page number and number of record perpage
			int limit = perpage;
			int offset = page * perpage - perpage ;
			
			//set direction and sorting
			Direction sortDirection = Direction.ASC;
			if(sort.toUpperCase().equals("DESC")) {
				sortDirection = Direction.DESC;
			}
			
			if(search == null || search.equals(null)) {
				search = "";
			}
			
			if(sortField.equals("name")) {
				sortField = "User_Name";
			}else if(sortField.equals("name2")) {
				sortField = "Agency_Name";
			}else if(sortField.equals("title")) {
				sortField = "Training_Title";
			}else if(sortField.equals("status")) {
				sortField = "Training_Status";
			}
			
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = trainingTxService.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase(search, search, search, pageable);
		
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = trainingTxService.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrTraining_TitleContainingIgnoreCase( search, search, search);
			
			int total = 0;
			double perpageDb = 0.0;
		
			try {
				total = totalL.intValue();
				perpageDb = (double) perpage;
			}catch(Exception e) {}
			
			pages = (int) Math.ceil(total / perpageDb);
			
			result += "{";
			result += "\"meta\":{";
			result += "\"page\":"+page+",";
			result += "\"pages\":"+pages+",";
			result += "\"perpage\":"+perpage+",";
			result += "\"total\":"+total+",";
			result += "\"sort\":\""+sort+"\",";
			result += "\"field\":\"id\"";
			result += "},";
			result += "\"data\":"+jsonRes+"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	    return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrainingTx(@PathVariable("id") String id) {
        logger.info("Fetching training transaction with id {}", id);
        TrainingTx trainingTx = trainingTxService.findById(id);
        if (trainingTx == null) {
            logger.error("training transaction with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TrainingTx>(trainingTx, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/list2/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getTcTx(@PathVariable("id") String id) throws JsonProcessingException {
        logger.info("Fetching training transaction with id {}", id);
        
        List<TrainingTx> ls = new ArrayList<TrainingTx>();
		ls = trainingTxService.findByTraining_id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@RequestMapping(value = "/trainingTx/list/{id}", method = RequestMethod.POST)
    public String trainingUser(@PathVariable("id") String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;
		
		List<TrainingTx> ls = new ArrayList<TrainingTx>();
		//ls = trainingTxService.findByTraining_id(id);
		
		try {
			
			//calculate page number and number of record perpage
			int limit = perpage;
			int offset = page * perpage - perpage ;
			
			//set direction and sorting
			Direction sortDirection = Direction.ASC;
			if(sort.toUpperCase().equals("DESC")) {
				sortDirection = Direction.DESC;
			}
			
			if(search == null || search.equals(null)) {
				search = "";
			}
			
			if(sortField.equals("name")) {
				sortField = "User_Name";
			}else if(sortField.equals("name2")) {
				sortField = "Agency_Name";
			}else if(sortField.equals("title")) {
				sortField = "Training_Title";
			}else if(sortField.equals("status")) {
				sortField = "Training_Status";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = trainingTxService.findByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(id, search, id, search, id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = trainingTxService.countByTraining_IdAndUser_NameContainingIgnoreCaseOrTraining_IdAndUser_Agency_NameContainingIgnoreCaseOrTraining_IdAndTraining_TitleContainingIgnoreCaseOrTraining_IdAndTraining_StatusContainingIgnoreCase(id, search, id, search, id, search, id, search);
					
			int total = 0;
			double perpageDb = 0.0;
			
			try {
				total = totalL.intValue();
				perpageDb = (double) perpage;
			}catch(Exception e) {}
			
			pages = (int) Math.ceil(total / perpageDb);
			
			result += "{";
			result += "\"meta\":{";
			result += "\"page\":"+page+",";
			result += "\"pages\":"+pages+",";
			result += "\"perpage\":"+perpage+",";
			result += "\"total\":"+total+",";
			result += "\"sort\":\""+sort+"\",";
			result += "\"field\":\"id\"";
			result += "},";
			result += "\"data\":"+jsonRes+"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
        return result;
			
    }
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/training/user/list/{id}", method = RequestMethod.POST)
	    public  String getTrainingList( @PathVariable("id") String id,
	    		@RequestParam ("datatable[pagination][page]") int page,
	    		@RequestParam ("datatable[pagination][perpage]") int perpage,
	    		@RequestParam ("datatable[sort][sort]") String sort,
	    		@RequestParam ("datatable[sort][field]") String sortField,
	    		@RequestParam (value= "datatable[search]", required=false) String search
	    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;

		User user = userService.findById(id);
		
		List<TrainingTx> ls = trainingTxService.findByUser(user);
		
//        logger.info("Fetching training transaction with user {}", user);
//        List<TrainingTx> trainingTx = trainingTxService.findByUser(user);
//        if (trainingTx == null) {
//            logger.error("training transaction with user {} not found.", user);
//           /*return new ResponseEntity(new CustomErrorType("User with id " + user 
//                    + " not found"), HttpStatus.NOT_FOUND); */
//        }
		
		try {
			
			//calculate page number and number of record perpage
			int limit = perpage;
			int offset = page * perpage - perpage ;
			
			//set direction and sorting
			Direction sortDirection = Direction.ASC;
			if(sort.toUpperCase().equals("DESC")) {
				sortDirection = Direction.DESC;
			}
			
			if(search == null || search.equals(null)) {
				search = "";
			}
			
			if(sortField.equals("title")) {
				sortField = "Training_Title";
			}else if(sortField.equals("technology")) {
				sortField = "Training_Technology_Name";
			}else if(sortField.equals("place")) {
				sortField = "Training_Place";
			}else if(sortField.equals("status")) {
				sortField = "Training_Status";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = trainingTxService.findByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(id, search, id, search, id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = trainingTxService.countByUser_IdAndTraining_TitleContainingIgnoreCaseOrUser_IdAndTraining_Technology_NameContainingIgnoreCaseOrUser_IdAndTraining_DurationContainingIgnoreCaseOrUser_IdAndTraining_PlaceContainingIgnoreCase(id, search, id, search, id, search, id, search);
					
			int total = 0;
			double perpageDb = 0.0;
			
			try {
				total = totalL.intValue();
				perpageDb = (double) perpage;
			}catch(Exception e) {}
			
			pages = (int) Math.ceil(total / perpageDb);
			
			result += "{";
			result += "\"meta\":{";
			result += "\"page\":"+page+",";
			result += "\"pages\":"+pages+",";
			result += "\"perpage\":"+perpage+",";
			result += "\"total\":"+total+",";
			result += "\"sort\":\""+sort+"\",";
			result += "\"field\":\"id\"";
			result += "},";
			result += "\"data\":"+jsonRes+"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
        return result;
    }
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/create", method = RequestMethod.POST)
    public ResponseEntity<?> createTrainingTx(@RequestBody TrainingTx trainingTx, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", trainingTx);
        
        //User objUser = new User();
		//objUser = userService.findById(userid);
        
        //Training objtra = new Training();
		//objtra = trainingTxService.findById(trainingid);
        
        /*if(objUser != null && objtra != null) {
            trainingTx.setTraining(objtra);
            trainingTx.setUser(objUser);            
        }*/
        trainingTx.setId(UUIDGenerator.getInstance().getId());
        trainingTx.setCreatedBy(trainingTx.getCreatedBy());
        trainingTx.setCreatedDate(new Date());
        trainingTxService.save(trainingTx);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(trainingTx.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/trainingTx/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTrainingTx(@PathVariable("id") String id, @RequestBody TrainingTx trainingTx) {
        logger.info("Updating Training transaction with id {}", id);
 
        TrainingTx currentTrainingTx = new TrainingTx();
        
        if (id !=null && !"".equals(id)) {
        	currentTrainingTx = trainingTxService.findById(id);
        	
        	if (currentTrainingTx !=null) {
        		String status = trainingTx.getStatus();
            	
            	if ("2".equals(status)) {
            		
            		currentTrainingTx.setCoach_remarks(trainingTx.getCoach_remarks());
            		currentTrainingTx.setStatus(trainingTx.getStatus());
            		currentTrainingTx.setQualification(trainingTx.getQualification());
            		currentTrainingTx.setEvaluatedBy(trainingTx.getEvaluatedBy());
            		currentTrainingTx.setEvaluatedDate(new Date());
            		trainingTxService.save(currentTrainingTx);
            		
            	}else if ("3".equals(status)) {
            		
            		currentTrainingTx.setAdmin_remarks(trainingTx.getAdmin_remarks());
            		currentTrainingTx.setStatus(trainingTx.getStatus());
            		currentTrainingTx.setApprovedBy(trainingTx.getApprovedBy());
            		currentTrainingTx.setApprovedDate(new Date());
            		trainingTxService.save(currentTrainingTx);

            	}else if ("4".equals(status)) {
            		
            		currentTrainingTx.setAdmin_remarks(trainingTx.getAdmin_remarks());
            		currentTrainingTx.setStatus(trainingTx.getStatus());
            		currentTrainingTx.setApprovedBy(trainingTx.getApprovedBy());
            		currentTrainingTx.setApprovedDate(new Date());
            		trainingTxService.save(currentTrainingTx);
            		
            	}else if ("5".equals(status)) {
            		
            		currentTrainingTx.setStatus(trainingTx.getStatus());
            		currentTrainingTx.setApprovedBy(trainingTx.getApprovedBy());
            		currentTrainingTx.setApprovedDate(new Date());
            		trainingTxService.save(currentTrainingTx);
            		
            	}       	
            	
            }else {
            	 logger.error("Unable to update. TrainingTx with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to update. TrainingTx with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        trainingTxService.save(currentTrainingTx);
        return new ResponseEntity<TrainingTx>(currentTrainingTx, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTrainingTx(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Training transaction with id {}", id);
 
        TrainingTx trainingTx= trainingTxService.findById(id);
        if (trainingTx == null) {
            logger.error("Unable to delete. Training transaction with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        trainingTxService.deleteById(id);
        return new ResponseEntity<TrainingTx>(HttpStatus.NO_CONTENT);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/isExist", method = RequestMethod.POST)
    public ResponseEntity<?> trainingtxExist(@RequestBody TrainingTx trainingTx, UriComponentsBuilder ucBuilder) {
		
		String trainingId = trainingTx.getTraining().getId();
	    String userId = trainingTx.getUser().getId();
	    String status = "1";
	    
	    boolean isExist = trainingTxService.existsByUser_IdAndTraining_IdAndStatus(userId, trainingId, status);

	    if (isExist) {
	        logger.error("Unable to create Training. A Training for User with name {} already exist", trainingTx.getUser().getName());
	        return new ResponseEntity(new CustomErrorType("Unable to create Training. A Training for User with name " + 
	        		trainingTx.getUser().getName() + " already exist."),HttpStatus.CONFLICT);
	    }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(trainingTx.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
	
	public String removeLastChar(String str) {
		    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
		        str = str.substring(0, str.length() - 1);
		    }
		    return str;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingTx/listFb/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getXtvtFb(@PathVariable("id") String id) {
		logger.info("Fetching TrainingTx with id {}", id);
		
        List<TrainingTx> ls = new ArrayList<TrainingTx>();
        //:TODO remove rejected user
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		JwtUser currentUser = (JwtUser) auth.getPrincipal();
		String currentUserId = currentUser.getId();
		
		String role = trainingCoachController.getUserRole(id, currentUserId);
		
		if("ROLE_USER".equals(role)) {
			List<UserFeedbackTrax> fbTxLs = userFeedbackTraxService.findByParentidAndUser_IdAndType(id, currentUserId, "TRAINING");
			for(UserFeedbackTrax fbtx:fbTxLs) {
				UserFeedback fb = new UserFeedback();
				fb = userFeedbackService.findByFeedbacktraxid(fbtx.getId());
				
				TrainingTx txObj = new TrainingTx();
				txObj.setStatus(fb.getType());//temporary store type
				txObj.setId(fb.getFeedbacktraxid());//temporary store userFeedback Id

				ls.add(txObj);
				
			}
			
		}else if("ROLE_COACH".equals(role)) {
			ls = trainingTxService.findByTraining_idAndStatusNotAndStatusNot(id, "4", "5");
			
			 if (ls.size() > 0) {
	            for(TrainingTx obj: ls) {
	            	
	            	User cuObj  = obj.getUser();
	    			User newObj = new User();
	    			boolean isFbExist = false;
	    			
	    			String traxId = "";
	    			
	    			List<UserFeedbackTrax> fbTxLs = userFeedbackTraxService.findByParentidAndUser_IdAndType(id, cuObj.getId(), "TRAINING");
	    			for(UserFeedbackTrax fbtx:fbTxLs) {
	    				UserFeedback fb = userFeedbackService.findByFeedbacktraxid(fbtx.getId());
	    				String type = fb.getType();
	    				if("PKK".equals(type)) {
	    					isFbExist = true;
	    					traxId = fb.getFeedbacktraxid();
	    					break;
	    				}
	    			}
	            	
	    			String type = cuObj.getType();
	    			Agency agency = new Agency();
	    			if("GOV".equals(type)) {
	    				agency = cuObj.getAgency();
	    				if(agency==null) {
	    					agency.setName("");
	    				}
	    			}else {
	    				Company comp = cuObj.getCompany();
	    				if(comp!=null) {
	    					agency.setId(comp.getId());
	    					agency.setName(comp.getName());
	    				}else {
	    					agency.setName("");
	    				}
	    			}
	    			
	            	newObj.setId(cuObj.getId());
	        		newObj.setName(cuObj.getName());
	        		newObj.setEmail(cuObj.getEmail());
	        		newObj.setPosition(cuObj.getPosition());
	        		newObj.setAgency(agency);
	        		newObj.setEnabled(isFbExist);
	        		if(isFbExist) {
	        			newObj.setType(traxId);//temp save traxId
	        		}
	        		
	        		obj.setUser(newObj);
	            	
	            }
	            
	        }else {
	        	logger.error("coachingActivities with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("coachingActivities with id " + id 
	                    + " not found"), HttpStatus.NOT_FOUND);
	        }
		}
        
        return new ResponseEntity<List<TrainingTx>>(ls, HttpStatus.OK);
    }
}
