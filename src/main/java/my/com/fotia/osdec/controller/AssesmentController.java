package my.com.fotia.osdec.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.assesment.model.Assesment;
import my.com.fotia.osdec.assesment.model.AssesmentAnswer;
import my.com.fotia.osdec.assesment.model.AssesmentCollection;
import my.com.fotia.osdec.assesment.model.AssesmentQuestion;
import my.com.fotia.osdec.assesment.model.UserAssesment;
import my.com.fotia.osdec.assesment.model.UserAssesmentAnswer;
import my.com.fotia.osdec.assesment.model.UserAssesmentQuestion;
import my.com.fotia.osdec.assesment.model.UserAssesmentTrax;
import my.com.fotia.osdec.assesment.service.AssesmentAnswerService;
import my.com.fotia.osdec.assesment.service.AssesmentQuestionService;
import my.com.fotia.osdec.assesment.service.AssesmentService;
import my.com.fotia.osdec.assesment.service.UserAssesmentAnswerService;
import my.com.fotia.osdec.assesment.service.UserAssesmentQuestionService;
import my.com.fotia.osdec.assesment.service.UserAssesmentService;
import my.com.fotia.osdec.assesment.service.UserAssesmentTraxService;
import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.coaching.service.CoachingService;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.coaching.user.service.CoachingUserService;
import my.com.fotia.osdec.general.skill.model.Skill;
import my.com.fotia.osdec.general.skill.service.SkillService;
import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.technology.service.TechnologyService;
import my.com.fotia.osdec.user.model.AppAuthority;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.model.UserAuthorities;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.Question;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api/asses")
public class AssesmentController {
	
public static final Logger logger = LoggerFactory.getLogger(AssesmentController.class);
	
	@Autowired
	AssesmentService assesmentService;
	
	@Autowired
	AssesmentAnswerService assesmentAnswerService;
	
	@Autowired
	AssesmentQuestionService assesmentQuestionService;
	
	@Autowired
	UserAssesmentService userAssesmentService;
	
	@Autowired
	UserAssesmentAnswerService userAssesmentAnswerService;
	
	@Autowired
	UserAssesmentQuestionService userAssesmentQuestionService;
	
	@Autowired
	UserAssesmentTraxService userAssesmentTraxService;
	
	@Autowired
	SkillService skillService;
	
	@Autowired
	TechnologyService technologyService;
	
	@Autowired
	CoachingService coachingService;
	
	@Autowired
	CoachingUserService coachingUserService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAssesment(@PathVariable("id") String id) {
        logger.info("Fetching agency with id {}", id);
        Assesment obj = assesmentService.findById(id);
        if (obj == null) {
            logger.error("Assesment with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Assesment with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Assesment>(obj, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAssesment(@RequestBody Assesment assesment, UriComponentsBuilder ucBuilder) {
		
		ResponseEntity<?> result = null;
		
        logger.info("Creating Assesment : {}", assesment);
        
        String level = assesment.getLevel();
        String techName = assesment.getTechnology().getName();
        
        if((level != null && !"".equals(level)) && (techName != null && !"".equals(techName))) {
        	boolean isExist = assesmentService.existsByTechnology_NameAndLevel(techName, level);
        	if(isExist) {
        		
        		/*if(level == "BEGIN") {
        			level = "Permulaan";
        		}else if(level == "INTER") {
        			level = "Pertengahan";
        		}else {
        			level = "Mahir";
        		}
        		*/
        		return new ResponseEntity(new CustomErrorType(" Set Soalan Latihan Kendiri bagi Teknologi " + 
                		assesment.getTechnology().getName() + " dan Tahap "+ level +" telah Wujud"),HttpStatus.CONFLICT);
        	}else {
        		assesment.setId(UUIDGenerator.getInstance().getId());
                assesment.setCreatedby(assesment.getUser().getId());
                assesment.setCreatedon(new Date());
                
        		Assesment obj = new Assesment();
                obj = assesmentService.save(assesment);
                
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path("/api/assesment/{id}").buildAndExpand(assesment.getId()).toUri());
                return new ResponseEntity<Assesment>(obj, HttpStatus.OK);
        	}
        }else {
        	return new ResponseEntity(new CustomErrorType(" Sila Penuhkan Ruangan yang perlu diisi dengan Betul "),HttpStatus.CONFLICT);
        }
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAssesment(@PathVariable("id") String id, @RequestBody Assesment assesment) {
        logger.info("Updating Agency with id {}", id);
 
        Assesment currentObj = assesmentService.findById(id);
 
        if (currentObj != null) {
        	
        	String level = assesment.getLevel();
            String techName = assesment.getTechnology().getName();
            
            if((level != null && !"".equals(level)) && (techName != null && !"".equals(techName))) {
            	boolean isExist = assesmentService.existsByTechnology_NameAndLevelAndIdNot(techName, level, id);
            	if(isExist) {
            		
            		/*if(level == "BEGIN") {
            			level = "Permulaan";
            		}else if(level == "INTER") {
            			level = "Pertengahan";
            		}else {
            			level = "Mahir";
            		}*/
            		
            		return new ResponseEntity(new CustomErrorType(" Set Soalan Latihan Kendiri bagi Teknologi " + 
                    		assesment.getTechnology().getName() + " dan Tahap "+ level+" telah Wujud"),HttpStatus.CONFLICT);
            	}else {
            		currentObj.setModifiedby(assesment.getUser().getId());
                    currentObj.setModifiedon(new Date());
                    currentObj.setLevel(assesment.getLevel());
                    currentObj.setTechnology(assesment.getTechnology());
                    currentObj.setCategory(assesment.getCategory());
                    currentObj.setTitle(assesment.getTitle());
                    currentObj.setQuestionno(assesment.getQuestionno());
                    
                    assesmentService.save(currentObj);
                    
                    return new ResponseEntity<Assesment>(currentObj, HttpStatus.OK);
            	}
            }else {
            	return new ResponseEntity(new CustomErrorType(" Sila Penuhkan Ruangan yang perlu diisi dengan Betul "),HttpStatus.CONFLICT);
            }
            	
        }else {
        	logger.error("Unable to update. Assesment with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Assesment with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
    }
	
	@RequestMapping(value = "/que/create", method = RequestMethod.POST)
    public ResponseEntity<?> createQue(@RequestBody AssesmentQuestion que, UriComponentsBuilder ucBuilder) {
        logger.info("Creating AssesmentQuestion : {}", que);
 
        que.setId(UUIDGenerator.getInstance().getId());
        que.setAssesment(que.getAssesment());
        
        List<AssesmentAnswer> ls = new ArrayList();
        ls = que.getAssesmentAnswer();
        
        Iterator<AssesmentAnswer> itr = ls.iterator();
    	while (itr.hasNext()) {
    		AssesmentAnswer obj = new AssesmentAnswer();
    		obj = itr.next();
    		obj.setAssesmentQuestion(que);
    		obj.setId(UUIDGenerator.getInstance().getId());
    	}
    	que.setAssesmentAnswer(ls);
    	
        assesmentQuestionService.save(que);
        assesmentAnswerService.save(ls);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/asses/que/create/{id}").buildAndExpand(que.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAssesment(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Assesment with id {}", id);
 
        Assesment obj = assesmentService.findById(id);
        if (obj == null) {
            logger.error("Unable to delete. Assesment with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Assesment with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        List<AssesmentQuestion> ls = obj.getAssesmentQuestion();
        
        for (AssesmentQuestion queObj : ls) {
        	String idQue = queObj.getId();
        	if(idQue!=null && !"".equals(idQue)) {
    			try {
        			assesmentAnswerService.deleteByAssesId(idQue);
        		}catch(Exception e) {}
        		try {
        			assesmentQuestionService.deleteById(idQue);
        		}catch(Exception e) {}
    		}
        }
        assesmentService.deleteById(id);
        return new ResponseEntity<Assesment>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
    public String assesList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Assesment> ls = new ArrayList<Assesment>();
		
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
			
			ls = assesmentService.findByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase
					(search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = assesmentService.countByTitleContainingIgnoreCaseOrInstancenameContainingIgnoreCaseOrTechnology_NameContainingIgnoreCase
					(search, search, search);
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
	
	@RequestMapping(value = "/que/list", method = RequestMethod.POST, produces = "application/json")
    public String assesQueList(
    		@RequestParam (value="datatable[id]", required=false) String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		int total = 0;
		String jsonRes = "";
		double perpageDb = 0.0;
		
		List<AssesmentQuestion> ls = new ArrayList<AssesmentQuestion>();
		
		try {
			
			if(id!=null && !"".equals(id)) {
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
				
				ls = assesmentQuestionService.findByAssesment_IdAndQuestionContainingIgnoreCase(id, search, pageable);
				
				ObjectMapper mapper = new ObjectMapper();
				jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = assesmentQuestionService.countByAssesment_IdAndQuestionContainingIgnoreCase(id, search);
				total = totalL.intValue();
				
			}else {
				total = 0;
				jsonRes = "[]";
			}
			
			try {
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
	@RequestMapping(value = "/que/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQue(@PathVariable("id") String id) {
        logger.info("Fetching AssesmentQuestion with id {}", id);
        AssesmentQuestion obj = assesmentQuestionService.findById(id);
        if (obj == null) {
            logger.error("AssesmentQuestion with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("AssesmentQuestion with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AssesmentQuestion>(obj, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/que/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateQue(@PathVariable("id") String id, @RequestBody AssesmentQuestion assesmentQuestion) {
        logger.info("Updating assesmentQuestionService with id {}", id);
 
        AssesmentQuestion currentObj = assesmentQuestionService.findById(id);
 
        if (currentObj != null) {
            currentObj.setQuestion(assesmentQuestion.getQuestion());
            
            //delete all child
            assesmentAnswerService.deleteByAssesId(id);
            
            //add back all child
            List<AssesmentAnswer> ls = new ArrayList();
            ls = assesmentQuestion.getAssesmentAnswer();
            
            Iterator<AssesmentAnswer> itr = ls.iterator();
        	while (itr.hasNext()) {
        		AssesmentAnswer obj = new AssesmentAnswer();
        		obj = itr.next();
        		obj.setAssesmentQuestion(assesmentQuestion);
        		obj.setId(UUIDGenerator.getInstance().getId());
        	}
        	currentObj.setAssesmentAnswer(ls);
        	
        	assesmentAnswerService.save(ls);
            assesmentQuestionService.save(currentObj);
            
            return new ResponseEntity<AssesmentQuestion>(currentObj, HttpStatus.OK);
        }else {
        	logger.error("Unable to update. Agency with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Agency with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/que/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteQue(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting AssesmentQuestion with id {}", id);
 
        AssesmentQuestion obj = assesmentQuestionService.findById(id);
        if (obj == null) {
            logger.error("Unable to delete. Assesment with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Assesment with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        assesmentAnswerService.deleteByAssesId(id);
        assesmentQuestionService.deleteById(id);
        return new ResponseEntity<Assesment>(HttpStatus.NO_CONTENT);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/generate/question", method = RequestMethod.POST)
    public ResponseEntity<List<Assesment>> getAssesment(@RequestBody Assesment[] assesArr) {
		
		List<Assesment> resultLs = new ArrayList<Assesment>();
		
		boolean allExist = true;
		for(Assesment assesment: assesArr) {
			boolean isExist = userAssesmentService.existsByTechnology_IdAndLevelAndUserId
					(assesment.getTechnology().getId(), assesment.getLevel(), assesment.getUser().getId());
			
			if(!isExist) {
				allExist = false;
				break;
			}
		}
		
		if(!allExist) {
			for(Assesment assesment: assesArr) {
				boolean isExist = userAssesmentService.existsByTechnology_IdAndLevelAndUserId
						(assesment.getTechnology().getId(), assesment.getLevel(), assesment.getUser().getId());
				
				if(!isExist) {
					Random rand = new Random();
					
					List<Assesment> ls = assesmentService.findByTechnology_IdAndLevel(assesment.getTechnology().getId(), assesment.getLevel());
			        
					int limit = 1;
					int sizeQue = 0;
					int remove = 0;
					
					for(Assesment asses: ls) {
						limit = asses.getQuestionno();
						
						List<AssesmentQuestion> aqLs = asses.getAssesmentQuestion();
						sizeQue = aqLs.size();
						remove = sizeQue-limit;
						
						if(remove > 0) {//positive - need to remove equal to limit number
							for (int i = 0; i < remove; i++) {
						        int randomIndex = rand.nextInt(aqLs.size());
						        AssesmentQuestion randomElement = aqLs.get(randomIndex);
						        aqLs.remove(randomIndex);
						    }
						}
					}
					resultLs.addAll(ls);
				}
			}
			
			if(resultLs.isEmpty()) {
	            logger.error("Assesment for the user not found.");
	            return new ResponseEntity(new CustomErrorType("Assesment with user not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<List<Assesment>>(resultLs, HttpStatus.OK);
	        
		}else {
			logger.error("Unable to create Assesment. Assesment for user already exist");
            return new ResponseEntity(new CustomErrorType("Unable to create Assesment. Assesment for  User with name " + 
            		"already exist."),HttpStatus.CONFLICT);
		}
    }
	
	//save question history for the user
	@RequestMapping(value = "/history/question/create", method = RequestMethod.POST)
    public ResponseEntity<?> copyToUser(@RequestBody Assesment assesment, UriComponentsBuilder ucBuilder) {
		
		/*try {
			for(Assesment assesment: assesArr) {
				List<AssesmentQuestion> queLs = new ArrayList<AssesmentQuestion>();
		        List<AssesmentAnswer> ansLs = new ArrayList<AssesmentAnswer>();
		        List<UserAssesmentQuestion> uQueLs = new ArrayList<UserAssesmentQuestion>();
		        List<UserAssesmentAnswer> uAnsLs = new ArrayList<UserAssesmentAnswer>();
		        
		        UserAssesment ua = new UserAssesment();
		        ua.setId(UUIDGenerator.getInstance().getId());
		        ua.setCategory(assesment.getCategory());
		        ua.setLevel(assesment.getLevel());
		        ua.setQuestionno(assesment.getQuestionno());
		        ua.setTechnology(assesment.getTechnology());
		        ua.setCreatedon(new Date());
		        ua.setCreatedby(assesment.getUser().getId());
		        ua.setUserId(assesment.getUser().getId());
		        
		        queLs = assesment.getAssesmentQuestion();
		        if(!queLs.isEmpty()) {
		        	for(AssesmentQuestion que: queLs) {
		        		UserAssesmentQuestion uque = new UserAssesmentQuestion();
		        		String answer = null;
		        		
		        		uque.setId(UUIDGenerator.getInstance().getId());
		        		uque.setQuestion(que.getQuestion());
		        		uque.setUserAssesment(ua);
		        		//uque.setAsnwer("");
		        		
		        		uQueLs.add(uque);
		        		
		        		ansLs = que.getAssesmentAnswer();
		                if(!ansLs.isEmpty()) {
		                	for(AssesmentAnswer ans: ansLs) {
		                		UserAssesmentAnswer uAns = new UserAssesmentAnswer();
		                		uAns.setId(UUIDGenerator.getInstance().getId());
		                		uAns.setAnswer(ans.isAnswer());
		                		uAns.setOption(ans.getOption());
		                		uAns.setUserAssesmentQuestion(uque);
		                		
		                		uAnsLs.add(uAns);
		                	}
		                }
		        	}
		        }
		        
		        userAssesmentService.save(ua);
		        userAssesmentQuestionService.save(uQueLs);
		        userAssesmentAnswerService.save(uAnsLs);
			}
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/history/question/create/{id}").buildAndExpand("user").toUri());
	        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity(new CustomErrorType("Unable to create asses User History "),HttpStatus.CONFLICT);
		}*/
		 List<AssesmentQuestion> queLs = new ArrayList<AssesmentQuestion>(); 
        List<AssesmentAnswer> ansLs = new ArrayList<AssesmentAnswer>(); 
        List<UserAssesmentQuestion> uQueLs = new ArrayList<UserAssesmentQuestion>(); 
        List<UserAssesmentAnswer> uAnsLs = new ArrayList<UserAssesmentAnswer>(); 
         
        UserAssesment ua = new UserAssesment(); 
        ua.setId(UUIDGenerator.getInstance().getId()); 
        ua.setCategory(assesment.getCategory()); 
        ua.setLevel(assesment.getLevel()); 
        ua.setQuestionno(assesment.getQuestionno()); 
        ua.setTechnology(assesment.getTechnology()); 
        ua.setCreatedon(new Date()); 
        ua.setCreatedby(assesment.getUser().getId()); 
        ua.setUserId(assesment.getUser().getId()); 
         
        queLs = assesment.getAssesmentQuestion(); 
        if(!queLs.isEmpty()) { 
          for(AssesmentQuestion que: queLs) { 
            UserAssesmentQuestion uque = new UserAssesmentQuestion(); 
            String answer = null; 
             
            uque.setId(UUIDGenerator.getInstance().getId()); 
            uque.setQuestion(que.getQuestion()); 
            uque.setUserAssesment(ua); 
            //uque.setAsnwer(""); 
             
            uQueLs.add(uque); 
             
            ansLs = que.getAssesmentAnswer(); 
                if(!ansLs.isEmpty()) { 
                  for(AssesmentAnswer ans: ansLs) { 
                    UserAssesmentAnswer uAns = new UserAssesmentAnswer(); 
                    uAns.setId(UUIDGenerator.getInstance().getId()); 
                    uAns.setAnswer(ans.isAnswer()); 
                    uAns.setOption(ans.getOption()); 
                    uAns.setUserAssesmentQuestion(uque); 
                     
                    uAnsLs.add(uAns); 
                  } 
                } 
          } 
        } 
         
        userAssesmentService.save(ua); 

        userAssesmentQuestionService.save(uQueLs); 
        userAssesmentAnswerService.save(uAnsLs); 
         
        HttpHeaders headers = new HttpHeaders(); 
        headers.setLocation(ucBuilder.path("/api/asses/user/create/{id}").buildAndExpand(assesment.getId()).toUri()); 
        return new ResponseEntity<String>(headers, HttpStatus.CREATED); 
    }
	
	//calculate the mark of assesment for the user
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUserAsses(@RequestBody UserAssesmentTrax[] traxArr, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User AssesemntTrax : {}", traxArr);
        
        List<UserAssesmentTrax> rsTrax = new ArrayList<UserAssesmentTrax>();
        
        double totalMark = 0.0;
        double totalQuestion = 0.0;
        double mark = 0.0;
        
        String userId = "";
        String technologyId = "";
        String level = "";
        User currentUser = new User();
        
        for(UserAssesmentTrax trax: traxArr) {
        	
        	String answerId = trax.getAnswerId();
        	String questionId = trax.getQuestionId();
        	userId = trax.getUserId();
        	technologyId = trax.getTechnologyId();
        	level = trax.getLevel();
        	currentUser = trax.getUser();
        	
        	boolean isAnswer = assesmentQuestionService.existsByIdAndAssesmentAnswer_IdAndAssesmentAnswer_Answer
        			(questionId, answerId, true);
        	
        	int answer = 0;
        	if(isAnswer) {
        		answer = 1;
        		totalMark++;
        	}
        	
        	UserAssesmentTrax obj = new UserAssesmentTrax();
        	obj.setId(UUIDGenerator.getInstance().getId());
        	obj.setMark(answer);
        	obj.setAnswerId(answerId);
        	obj.setQuestionId(questionId);
        	obj.setCreatedby(userId);
        	obj.setCreatedon(new Date());
        	obj.setUserId(userId);
        	obj.setTechnologyId(technologyId);
        	obj.setLevel(level);
        	
        	rsTrax.add(obj);
        	totalQuestion++;
        }
        
        if(!rsTrax.isEmpty()) {
        	userAssesmentTraxService.save(rsTrax);
        	
        	mark = totalMark/totalQuestion * 100;
        	
        	if((userId!=null && !"".equals(userId)) && 
        		(technologyId!=null && !"".equals(technologyId)) && 
        		(level!=null && !"".equals(level))) {
        		
        		Skill usrSkill = skillService.findByUser_IdAndTechnology_IdAndLevel(userId, technologyId, level);
        		if(usrSkill != null) {
        			//insert mark into skill
        			usrSkill.setMark(mark);
        			skillService.save(usrSkill);
        		}else {
        			Skill obj = new Skill();
        			obj.setId(UUIDGenerator.getInstance().getId());
        			obj.setMark(mark);
        			obj.setLevel(level);
        			obj.setUser(currentUser);
        			
        			Technology techObj = technologyService.findById(technologyId);
        			if(techObj!=null) {
        				obj.setTechnology(techObj);
        			}
        			skillService.save(obj);
        		}
        		
        	}
        	
        }
        
        return new ResponseEntity<Double>(mark, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/update/status/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> update(@PathVariable("id") String coachingId) {
        
        Coaching obj = new Coaching();
        boolean allExist = true;
        boolean isIn = false;
        
        ResponseEntity<Coaching> returnRs = new ResponseEntity<Coaching>(obj, HttpStatus.OK);
        
        try {
        	
        	obj = coachingService.findById(coachingId);
        	if (obj == null) {
                logger.error("Unable to update. Coaching with id {} not found.", coachingId);
                return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + coachingId + " not found."),
                        HttpStatus.NOT_FOUND);
            }else {
            	
            	String frontId = obj.getFrontend().getId();
            	String frontLvl = obj.getFrontendlevel();
            	String backId = obj.getBackend().getId();
            	String backLvl = obj.getBackendlevel();
            	String dbId = obj.getDatabase().getId();
            	String dbLvl = obj.getDatabaselevel();
            	
            	String[] techIdArr = {
            			frontId, backId, dbId	
            	};
            	
            	String[] levelArr = {
            			frontLvl, backLvl, dbLvl	
            	};
            	
            	List<CoachingUser> userLs = coachingUserService.findByCoaching_id(coachingId);
            	
            	for(CoachingUser cu: userLs) {
            		String userId = cu.getUser().getId();
            		for(int i=0; i<3; i++) {
            			isIn = true;
            			boolean ueExist = userAssesmentService.existsByTechnology_IdAndLevelAndUserId(techIdArr[i], levelArr[i], userId);
            			if(!ueExist) {
            				allExist = false;
            				break;
            			}
            		}
            	}
            	
            	if(allExist && isIn) {
            		//update status coaching to 1
            		coachingService.updateStatus("1", coachingId);
            	}else {
            		//all user in coaching nned to finish the assesment
            		returnRs =  new ResponseEntity(new CustomErrorType("Please completed All Assesment!"),HttpStatus.CONFLICT);
            	}
            	return returnRs;
            }
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	logger.error("Unable to update. Coaching with id {} not found.", coachingId);
            return new ResponseEntity(new CustomErrorType("Unable to upate. coachingId with id " + coachingId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
    }
	
	//save question history for the user
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseEntity<?> copyToUser(@RequestBody AssesmentCollection assesmentCollection) {
		
		Assesment assesment = assesmentCollection.getAssesment();
		UserAssesmentTrax[] traxArr = assesmentCollection.getUserAssesmentTrax();
		double mark = 0;
		double totalQuestion = 0;
		double totalMark = 0;
		String result = "";
		String totalMarkStr = "";
		
		List<AssesmentQuestion> queLs = new ArrayList<AssesmentQuestion>(); 
        List<AssesmentAnswer> ansLs = new ArrayList<AssesmentAnswer>(); 
        List<UserAssesmentQuestion> uQueLs = new ArrayList<UserAssesmentQuestion>(); 
        List<UserAssesmentAnswer> uAnsLs = new ArrayList<UserAssesmentAnswer>();
        
        String level = assesment.getLevel();
        String technologyId = assesment.getTechnology().getId();
        String userId = assesment.getUser().getId();
         
        UserAssesment ua = new UserAssesment();
        ua.setId(UUIDGenerator.getInstance().getId());
        ua.setCategory(assesment.getCategory());
        ua.setLevel(level);
        ua.setQuestionno(assesment.getQuestionno());
        ua.setTechnology(assesment.getTechnology());
        ua.setCreatedon(new Date());
        ua.setCreatedby(userId);
        ua.setUserId(userId);
         
        queLs = assesment.getAssesmentQuestion();
        if(!queLs.isEmpty()) {
          for(AssesmentQuestion que: queLs) {
            UserAssesmentQuestion uque = new UserAssesmentQuestion();
            
            String userAnswer = "";
            
            for(UserAssesmentTrax trax: traxArr) {
            	
            	String answerId = trax.getAnswerId();
            	String questionId = trax.getQuestionId();
            	
            	if(questionId.equals(que.getId())) {
            		uque.setAnswer(answerId);
            		userAnswer = answerId;
            	}
            }
             
            uque.setId(que.getId());
            uque.setQuestion(que.getQuestion());
            uque.setUserAssesment(ua);
             
            uQueLs.add(uque);
             
            ansLs = que.getAssesmentAnswer();
            totalQuestion = queLs.size();
            
            if(!ansLs.isEmpty()) {
              for(AssesmentAnswer ans: ansLs) {
                UserAssesmentAnswer uAns = new UserAssesmentAnswer();
                
                boolean ansAnswer = ans.isAnswer();
                String ansId = ans.getId();
                if((ansAnswer == true) && (ansId.equals(userAnswer))) {
                	mark++;
                }
                
                uAns.setId(ansId);
                uAns.setAnswer(ansAnswer);
                uAns.setOption(ans.getOption());
                uAns.setUserAssesmentQuestion(uque);
                 
                uAnsLs.add(uAns);
              } 
            } 
          } 
        } 
         
        userAssesmentService.save(ua); 

        userAssesmentQuestionService.save(uQueLs); 
        userAssesmentAnswerService.save(uAnsLs);
        
        totalMark = (mark / totalQuestion) * 100;
        totalMarkStr = new DecimalFormat("#").format(totalMark);
        
        //save mark into skill
        if((userId!=null && !"".equals(userId)) && 
    		(technologyId!=null && !"".equals(technologyId)) && 
    		(level!=null && !"".equals(level))) {
    		
    		Skill usrSkill = skillService.findByUser_IdAndTechnology_IdAndLevel(userId, technologyId, level);
    		if(usrSkill != null) {
    			//insert mark into skill
    			usrSkill.setMark(totalMark);
    			skillService.save(usrSkill);
    		}else {
    			Skill obj = new Skill();
    			obj.setId(UUIDGenerator.getInstance().getId());
    			obj.setMark(totalMark);
    			obj.setLevel(level);
    			obj.setUser(assesment.getUser());
    			
    			Technology techObj = technologyService.findById(technologyId);
    			if(techObj!=null) {
    				obj.setTechnology(techObj);
    			}
    			skillService.save(obj);
    		}
    	}
        //:TODO;
        
        result += "{";
		result += "\"totalMark\":"+totalMarkStr+",";
		result += "\"total\":"+totalQuestion+",";
		result += "\"mark\":"+mark;
		result += "}";
		
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
	
	//check current task for coaching's assesment
	@RequestMapping(value = "/task/coaching/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Coaching>> existCoachingTask(@PathVariable("id") String userId) {
		
		List<CoachingUser> cuLs = coachingUserService.findByUser_Id(userId);
		
		List<Coaching> coachingLs = new ArrayList<Coaching>();
		
		for(CoachingUser cuObj: cuLs) {
			Coaching coaching = cuObj.getCoaching();
			if(coaching!=null) {
				
				String frontId = coaching.getFrontend().getId();
            	String frontLvl = coaching.getFrontendlevel();
            	String backId = coaching.getBackend().getId();
            	String backLvl = coaching.getBackendlevel();
            	String dbId = coaching.getDatabase().getId();
            	String dbLvl = coaching.getDatabaselevel();
            	
            	String[] techIdArr = {
            			frontId, backId, dbId	
            	};
            	
            	String[] levelArr = {
            			frontLvl, backLvl, dbLvl	
            	};
            	
            	for(int i=0; i<3; i++) {
	            	boolean ueExist = true;
	            	if((techIdArr[i] != null && !"".equals(techIdArr[i])) && 
	            			(levelArr[i] != null && !"".equals(levelArr[i])) && 
	            			(userId != null && !"".equals(userId))) {
	            		ueExist = userAssesmentService.existsByTechnology_IdAndLevelAndUserId(techIdArr[i], levelArr[i], userId);
		    			if(!ueExist) {
		    				coachingLs.add(coaching);
		    				break;
		    			}
	            	}
	            	
            	}
			}
		}
         
        return new ResponseEntity<List<Coaching>>(coachingLs, HttpStatus.OK);
    }
}
