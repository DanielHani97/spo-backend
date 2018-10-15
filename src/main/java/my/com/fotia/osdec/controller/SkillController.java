package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import my.com.fotia.osdec.assesment.model.AssesmentAnswer;
import my.com.fotia.osdec.general.skill.model.Skill;
import my.com.fotia.osdec.general.skill.service.SkillService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class SkillController {
	
public static final Logger logger = LoggerFactory.getLogger(SkillController.class);
	
	@Autowired
	SkillService skillService;

	@RequestMapping(value = "/skill/all", method = RequestMethod.POST, produces = "application/json")
	public String skillList() throws Exception {		
		String result = "";
		
		List<Skill> ls = new ArrayList<Skill>();
		ls = skillService.skillList();
		
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
	@RequestMapping(value = "/skill/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSkill(@PathVariable("id") String id) {
        logger.info("Fetching skill with id {}", id);
        Skill skill = skillService.findById(id);
        if (skill == null) {
            logger.error("skill with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Skill>(skill, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/skill/create", method = RequestMethod.POST)
    public ResponseEntity<?> createSkill(@RequestBody List<Skill> ls, UriComponentsBuilder ucBuilder) {
		
		String userid = "";
		List<Skill> skillLs = new ArrayList<Skill>();
		
        Iterator<Skill> itr = ls.iterator();
    	while (itr.hasNext()) {
    		Skill obj = new Skill();
    		obj = itr.next();
    		obj.setId(UUIDGenerator.getInstance().getId());
    		
    		userid = obj.getUser().getId();
    	}
    	
    	//delete all data
    	try {
			
			skillLs = skillService.findByUser_Id(userid);
			
			if(skillLs.size()>0) {
				skillService.deleteById(userid);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
    	
    	for(Skill skill:ls) {
    		String currentTechId = skill.getTechnology().getId();
    		double mark = 0;
    		
    		for(Skill objDb:skillLs) {
    			String techIdDb = objDb.getTechnology().getId();
    			if(currentTechId.equals(techIdDb)) {
    				mark = objDb.getMark();
    			}
    		}
    		skill.setMark(mark);
    	}
        
    	//save all data back
        skillService.save(ls);
 
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(new CustomErrorType("Save list of Skill"),HttpStatus.CREATED);
    }
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/skill/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSkill(@PathVariable("id") String id, @RequestBody Skill skill) {
        logger.info("Updating Skill with id {}", id);
 
        Skill currentSkill = new Skill();
        if(id !=null && !"".equals(id)) {
        		currentSkill = skillService.findById(id);
        		if(currentSkill != null) {
        			currentSkill.setUser_id(skill.getUser_id());
        			currentSkill.setSkills(skill.getSkills());
        			currentSkill.setExpertise_level(skill.getExpertise_level());
        			skillService.save(currentSkill);
        	
        			
        		
        } else  {
            logger.error("Unable to update. Skill with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Skill with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        
        
        return new ResponseEntity<Skill>(currentSkill, HttpStatus.OK);
    }*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/skill/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSkill(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Skill with id {}", id);
 
        Skill skill = skillService.findById(id);
        if (skill == null) {
            logger.error("Unable to delete. Skill with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        skillService.deleteById(id);
        return new ResponseEntity<Skill>(HttpStatus.NO_CONTENT);
    }

}
