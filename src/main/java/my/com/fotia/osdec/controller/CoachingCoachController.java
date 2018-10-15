package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.List;

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

import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.coaching.coach.service.CoachingCoachService;
import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CoachingCoachController {
	
	public static final Logger logger = LoggerFactory.getLogger(CoachingCoachController.class);
	
	@Autowired
	CoachingCoachService coachingCoachService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/coachingCoach/all", method = RequestMethod.POST, produces = "application/json")
    public String coachingCoachList() throws Exception {
		
		String result = "";
		
		List<CoachingCoach> ls = new ArrayList<CoachingCoach>();
		ls = coachingCoachService.coachingCoachList();
		
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
	
	@RequestMapping(value = "/coachingCoach/getall/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getCoach(@PathVariable("id") String id) throws Exception {
		
		List<CoachingCoach> ls = new ArrayList<CoachingCoach>();
		ls = coachingCoachService.findByCoaching_Id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingCoach/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoachingCoach(@PathVariable("id") String id) {
        logger.info("Fetching coachingCoach with id {}", id);
        CoachingCoach coachingCoach = coachingCoachService.findById(id);
        if (coachingCoach == null) {
            logger.error("coachingCoach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CoachingCoach>(coachingCoach, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coaching/coach/list/{id}", method = RequestMethod.POST)
    public  String getCoachingCoachList(
    		@PathVariable("id") String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 2;
		
		User coach = userService.findById(id);
		
        List<CoachingCoach> ls = coachingCoachService.findByCoach(coach);
        
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
			
			ls = coachingCoachService.findByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = coachingCoachService.countByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(id, search, id, search);
			
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
	
	@RequestMapping(value = "/coachingCoach/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCoachingCoach(@RequestBody CoachingCoach coachingCoach, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", coachingCoach);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        coachingCoach.setId(UUIDGenerator.getInstance().getId());
        
        coachingCoachService.save(coachingCoach);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(coachingCoach.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coachingCoach/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoachingCoach(@PathVariable("id") String id, @RequestBody CoachingCoach coachingCoach) {
        logger.info("Updating coachingCoach with id {}", id);
        
        CoachingCoach currentCoachingCoach = new CoachingCoach();
 
        if(id !=null && !"".equals(id)) {
        	currentCoachingCoach = coachingCoachService.findById(id);
            
            if(currentCoachingCoach != null) {
            
            	//currentCoachingCoach.setStatus(CoachingCoach.getStatus());
            	coachingCoachService.save(currentCoachingCoach);
            	
            }else {
            	 logger.error("Unable to update. CoachingCoach with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. CoachingCoach with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
        }
        //Agency currentAgency = agencyService.findById(id);
 
        
        
        //Agency currentAgency = agencyService.findById(id);
        
        
        
        
        
        return new ResponseEntity<CoachingCoach>(currentCoachingCoach, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coachingCoach/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoachingCoach(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting CoachingCoach with id {}", id);
 
        CoachingCoach coachingCoach = coachingCoachService.findById(id);
        if (coachingCoach == null) {
            logger.error("Unable to delete. CoachingCoach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        coachingCoachService.deleteById(id);
        return new ResponseEntity<CoachingCoach>(HttpStatus.NO_CONTENT);
    }

}