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
import my.com.fotia.osdec.trainingCoach.model.TrainingCoach;
import my.com.fotia.osdec.trainingCoach.service.TrainingCoachService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class TrainingCoachController {
	
public static final Logger logger = LoggerFactory.getLogger(TrainingCoachController.class);
	
	@Autowired
	TrainingCoachService trainingCoachService;
	@Autowired
	UserService userService;	
	
	@RequestMapping(value = "/trainingCoach/all", method = RequestMethod.POST, produces = "application/json")
    public String trainingCoachList() throws Exception {
		
		String result = "";
		
		List<TrainingCoach> ls = new ArrayList<TrainingCoach>();
		ls = trainingCoachService.trainingCoachList();
		
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
	@RequestMapping(value = "/trainingCoach/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrainingCoach(@PathVariable("id") String id) {
        logger.info("Fetching training coach with id {}", id);
        TrainingCoach trainingCoach = trainingCoachService.findById(id);
        if (trainingCoach == null) {
            logger.error("training coach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TrainingCoach>(trainingCoach, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/trainingCoach/list/{id}", method = RequestMethod.GET, produces = "application/json")
    public String trainingUser(@PathVariable("id") String id) throws Exception {
		
		List<TrainingCoach> ls = new ArrayList<TrainingCoach>();
		ls = trainingCoachService.findByTraining_id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }

		@RequestMapping(value = "/training/coach/list/{id}", method = RequestMethod.POST, produces = "application/json")
	    public String trainingList (@PathVariable("id") String id,
			@RequestParam ("datatable[pagination][page]") int page,
			@RequestParam ("datatable[pagination][perpage]") int perpage,
			@RequestParam ("datatable[sort][sort]") String sort,
			@RequestParam ("datatable[sort][field]") String sortField,
			@RequestParam (value= "datatable[search]", required=false) String search
			) throws Exception {
		
			String result = "";
			User coach = userService.findById(id);
			int pages = 0;
						
			List<TrainingCoach> ls = trainingCoachService.findByCoach(coach);
			
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
			}else if(sortField.equals("duration")) {
				sortField = "Training_Duration";
			}else if(sortField.equals("place")) {
				sortField = "Training_Place";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = trainingCoachService.findByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
					(id, search, id, search, id, search, id, search, pageable);
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = trainingCoachService.countByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
					(id, search, id, search, id, search, id, search);
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
	
	@RequestMapping(value = "/trainingCoach/create", method = RequestMethod.POST)
    public ResponseEntity<?> createTrainingCoach(@RequestBody TrainingCoach trainingCoach, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", trainingCoach);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        trainingCoach.setId(UUIDGenerator.getInstance().getId());
        
        trainingCoachService.save(trainingCoach);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(trainingCoach.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/trainingCoach/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTrainingCoach(@PathVariable("id") String id, @RequestBody TrainingCoach trainingCoach) {
        logger.info("Updating Training coach with id {}", id);
 
        TrainingCoach currentTrainingCoach = new TrainingCoach();
        
        if (id !=null && !"".equals(id)) {
        	currentTrainingCoach = trainingCoachService.findById(id);
        	
        	if (currentTrainingCoach !=null) {
                
        		trainingCoachService.save(currentTrainingCoach);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Training coach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Training coach with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<TrainingCoach>(currentTrainingCoach, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/trainingCoach/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTrainingCoach(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Training coach with id {}", id);
 
        TrainingCoach trainingCoach = trainingCoachService.findById(id);
        if (trainingCoach == null) {
            logger.error("Unable to delete. Training coach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        trainingCoachService.deleteById(id);
        return new ResponseEntity<TrainingCoach>(HttpStatus.NO_CONTENT);
    }
	
	public String getUserRole(String trainingId, String userId) {
		String roleResult = "ROLE_USER";
		
		boolean isCoach = trainingCoachService.existsByTraining_IdAndCoach_Id(trainingId, userId);
		if(isCoach) {
			roleResult = "ROLE_COACH";
		}
		
		return roleResult;
	}
	
	@RequestMapping(value = "/training/role/{trainingid}/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeedback(@PathVariable("userid") String userid, @PathVariable("trainingid") String trainingid) {
        logger.info("Fetching trainingrole with trainingid {}", trainingid);
        
        String role = getUserRole(trainingid, userid);
        
        return new ResponseEntity<String>(role, HttpStatus.OK);
    }

}
