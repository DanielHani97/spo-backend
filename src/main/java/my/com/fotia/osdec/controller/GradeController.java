package my.com.fotia.osdec.controller;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.general.grade.model.Grade;

import my.com.fotia.osdec.general.grade.service.GradeService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class GradeController {
	
public static final Logger logger = LoggerFactory.getLogger(GradeController.class);
	
	@Autowired
	GradeService gradeService;
	
	@RequestMapping(value = "/grade", method = RequestMethod.POST, produces = "application/json")
    public String gradeList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search 
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Grade> ls = new ArrayList<Grade>();
		
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
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = gradeService.findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search,  search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = gradeService.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search);
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
	
	@RequestMapping(value = "/grade/count", method = RequestMethod.GET, produces ="application/json")
	public ResponseEntity<List<Integer>>gradeCnList(){	
												
		Long countall = gradeService.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
				("","");
		
		int count = 0;
				try {
			count = countall.intValue();			
		}catch(Exception e) {}
		
		List<Integer> ls = new ArrayList<>();
		for(int i = 0; i < count+1 ; i++ ) {						
			ls.add(i+1);
		}
		
		return new ResponseEntity<List<Integer>>(ls, HttpStatus.OK);
	
		
	}
		
	

	@RequestMapping(value = "/grade/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Grade>> gradeList() {
		List<Grade> list = gradeService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Grade>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Grade>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/grade/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getGrade(@PathVariable("id") String id) {
        logger.info("Fetching grade with id {}", id);
        Grade grade = gradeService.findById(id);
        if (grade == null) {
            logger.error("grade with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Grade with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Grade>(grade, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/grade/create", method = RequestMethod.POST)
    public ResponseEntity<?> createGrade(@RequestBody Grade grade, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Grade : {}", grade);
 
        
        grade.setId(UUIDGenerator.getInstance().getId());
        
        gradeService.save(grade);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(grade.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/grade/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateGrade(@PathVariable("id") String id, @RequestBody Grade grade) {
        logger.info("Updating Grade with id {}", id);
 
        Grade currentGrade = gradeService.findById(id);
        
        if(currentGrade == null) {
        	logger.error("Unable to update. Grade with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Grade with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        			
        		
        } 
        
        	currentGrade.setName(grade.getName());
        	currentGrade.setSeniority(grade.getSeniority());
        	currentGrade.setStatus(grade.getStatus());
        	
        	gradeService.save(currentGrade);
        	return new ResponseEntity<Grade>(currentGrade, HttpStatus.OK);
        	
        }
               
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/grade/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGrade(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Grade with id {}", id);
 
        Grade grade = gradeService.findById(id);
        if (grade == null) {
            logger.error("Unable to delete. Grade with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Grade with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        gradeService.deleteById(id);
        return new ResponseEntity<Grade>(HttpStatus.NO_CONTENT);
    }
}
