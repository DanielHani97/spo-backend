package my.com.fotia.osdec.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
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

import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.coaching.user.service.CoachingUserService;
import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CoachingUserController {
	
	public static final Logger logger = LoggerFactory.getLogger(CoachingUserController.class);
	
	@Autowired
	CoachingUserService coachingUserService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/coachingUser/getall/{id}", method = RequestMethod.POST, produces = "application/json")
    public List<CoachingUser> coachingList(@PathVariable("id") String id) throws Exception {
		
		List<CoachingUser> ls = new ArrayList <CoachingUser>();		
		ls = coachingUserService.findByUser_Id(id);
				
		return ls;
	}
	
	@RequestMapping(value = "/coachingUser/all", method = RequestMethod.POST, produces = "application/json")
    public String coachingUserList() throws Exception {
		
		String result = "";
		
		List<CoachingUser> ls = new ArrayList<CoachingUser>();
		ls = coachingUserService.coachingUserList();
		
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingUser/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoachingUser(@PathVariable("id") String id) {
        logger.info("Fetching coachingUser with id {}", id);
        CoachingUser coachingUser = coachingUserService.findById(id);
        if (coachingUser == null) {
            logger.error("coachingUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CoachingUser>(coachingUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingUser/getCoaching/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoachingByCUser(@PathVariable("id") String id) {
        logger.info("Fetching coachingUser with id {}", id);
        CoachingUser coachingUser = coachingUserService.findById(id);
        if (coachingUser == null) {
            logger.error("coachingUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }else {
        	Coaching coaching = coachingUser.getCoaching();
        	return new ResponseEntity<Coaching>(coaching, HttpStatus.OK);
        }
        
    }
	
	@RequestMapping(value = "/coachingUser/list/{id}", method = RequestMethod.GET, produces = "application/json")
    public String coachingUser(@PathVariable("id") String id) throws Exception {
		
		List<CoachingUser> ls = new ArrayList<CoachingUser>();
		ls = coachingUserService.findByCoaching_id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@RequestMapping(value = "/coachingUser/user/{id}", method = RequestMethod.GET, produces = "application/json")
    public String coachingUserList(@PathVariable("id") String id) throws Exception {
		
		List<CoachingUser> ls = new ArrayList<CoachingUser>();
		ls = coachingUserService.findByUser_Id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coaching/user/list/{id}", method = RequestMethod.POST)
    public  String getCoachingList(@PathVariable("id")String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		
		String result = "";
		int pages = 1;
		
		User user = userService.findById(id);
		
        List<CoachingUser> ls = coachingUserService.findByUser(user);
        
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
				sortField = "Coaching_Name";
			}else if(sortField.equals("agency")) {
				sortField = "Coaching_Agency_Name";
			}else if(sortField.equals("status")) {
				sortField = "Coaching_Status";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = coachingUserService.findByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = coachingUserService.countByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase(id, search, id, search);
					
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
	
	@RequestMapping(value = "/coachingUser/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCoachingUser(@RequestBody CoachingUser coachingUser, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", coachingUser);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        coachingUser.setId(UUIDGenerator.getInstance().getId());
        
        
        coachingUserService.save(coachingUser);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(coachingUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coachingUser/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoachingUser(@PathVariable("id") String id, @RequestBody CoachingUser coachingUser) {
        logger.info("Updating coachingUser with id {}", id);
        
        CoachingUser currentCoachingUser = new CoachingUser();
 
        if(id !=null && !"".equals(id)) {
        	currentCoachingUser = coachingUserService.findById(id);
            
            if(currentCoachingUser != null) {
            	
            	currentCoachingUser.setUser(coachingUser.getUser());
            	currentCoachingUser.setCoaching(coachingUser.getCoaching());
            	currentCoachingUser.setAdmin_remarks(coachingUser.getAdmin_remarks());
            	currentCoachingUser.setCoach_remarks(coachingUser.getCoach_remarks());
            	currentCoachingUser.setRole(coachingUser.getRole());
            	
            	coachingUserService.save(currentCoachingUser);
            	
            }else {
            	 logger.error("Unable to update. CoachingUser with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. CoachingUser with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
        }

        
        return new ResponseEntity<CoachingUser>(currentCoachingUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingUser/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoachingUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting CoachingUser with id {}", id);
 
        CoachingUser coachingUser = coachingUserService.findById(id);
        if (coachingUser == null) {
            logger.error("Unable to delete. CoachingUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        coachingUserService.deleteById(id);
        return new ResponseEntity<CoachingUser>(HttpStatus.NO_CONTENT);
    }
	
	public String removeLastChar(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
}

}