package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.capability.model.CapabilityActivities;
import my.com.fotia.osdec.capability.service.CapabilityActivitiesService;
import my.com.fotia.osdec.capability.service.CapabilityCoachService;
import my.com.fotia.osdec.capability.service.CapabilityUserService;
import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;
import my.com.fotia.osdec.coaching.activities.service.CoachingActivitiesService;
import my.com.fotia.osdec.coaching.coach.service.CoachingCoachService;
import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.coaching.service.CoachingService;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.coaching.user.service.CoachingUserService;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.FeedbackCollection;
import my.com.fotia.osdec.feedback.model.FeedbackSection;
import my.com.fotia.osdec.feedback.model.FeedbackSectionQuestion;
import my.com.fotia.osdec.feedback.model.UserFeedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackSection;
import my.com.fotia.osdec.feedback.model.UserFeedbackSectionQuestion;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;
import my.com.fotia.osdec.feedback.service.FeedbackSectionQuestionService;
import my.com.fotia.osdec.feedback.service.FeedbackSectionService;
import my.com.fotia.osdec.feedback.service.FeedbackService;
import my.com.fotia.osdec.feedback.service.UserFeedbackSectionQuestionService;
import my.com.fotia.osdec.feedback.service.UserFeedbackSectionService;
import my.com.fotia.osdec.feedback.service.UserFeedbackService;
import my.com.fotia.osdec.feedback.service.UserFeedbackTraxService;
import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.service.AgencyService;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.training.service.TrainingService;
import my.com.fotia.osdec.trainingCoach.service.TrainingCoachService;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.trainingTx.service.TrainingTxService;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.GlobalStatic;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	public static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	
	@Autowired
	FeedbackService feedbackService;
	
	@Autowired
	FeedbackSectionService feedbackSectionService;
	
	@Autowired
	FeedbackSectionQuestionService feedbackSectionQuestionService;
	
	@Autowired
	CoachingActivitiesService coachingActivitiesService;
	
	@Autowired
	CoachingUserService coachingUserService;
	
	@Autowired
	CoachingCoachService coachingCoachService;
	
	@Autowired
	CoachingService coachingService;
	
	@Autowired
	UserFeedbackService userFeedbackService;
	
	@Autowired
	UserFeedbackTraxService userFeedbackTraxService;
	
	@Autowired
	UserFeedbackSectionService userFeedbackSectionService;
	
	@Autowired
	UserFeedbackSectionQuestionService userFeedbackSectionQuestionService;
	
	@Autowired
	CoachingController coachingController;
	
	@Autowired
	TrainingTxService trainingTxService;
	
	@Autowired
	TrainingCoachService trainingCoachService;
	
	@Autowired
	TrainingService trainingService;
	
	@Autowired
	CapabilityActivitiesService capabilityActivitiesService;
	
	@Autowired
	CapabilityUserService capabilityUserService;
	
	@Autowired
	CapabilityCoachService capabilityCoachService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
    public String feedbackList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Feedback> ls = new ArrayList<Feedback>();
		
		try {
			
			ls = feedbackService.findAll();
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = feedbackService.count();
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
	@RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackCollection feedbackCollection, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Feedback : {}", feedbackCollection);
        
        Feedback feedback = new Feedback();
        FeedbackSection[] secArr = feedbackCollection.getFeedbackSection();
        FeedbackSectionQuestion[] queArr = feedbackCollection.getFeedbackSectionQuestion();
        
        try {
        	
        	feedback = feedbackCollection.getFeedback();
        	String id = feedback.getId();
        	Feedback fbDb = feedbackService.findById(id);
        	
        	if(fbDb != null) {
        		
        		feedback.setType(fbDb.getType());
        		feedbackService.save(feedback);
        		
        		//delete all section/question by Id
        		for(FeedbackSectionQuestion que: queArr) {
        			boolean isExist = feedbackSectionQuestionService.existsById(que.getId());
        			if(isExist) {
        				feedbackSectionQuestionService.deleteById(que.getId());
        			}
        		}
        		
        		for(FeedbackSection section: secArr) {
        			boolean isExist = feedbackSectionService.existsById(section.getId());
        			if(isExist) {
        				try{
        					feedbackSectionQuestionService.deleteByFeedbackSectionId(section.getId());
        				}catch(Exception e) {}
        				feedbackSectionService.deleteById(section.getId());
        			}
        		}
        		
        	}else {
        		logger.error("feedback with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("feedback with id " + id 
                        + " not found"), HttpStatus.NOT_FOUND);
        	}
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeedback(@PathVariable("id") String id) {
        logger.info("Fetching Feedback with id {}", id);
        Feedback feedback = feedbackService.findById(id);
        if (feedback == null) {
            logger.error("feedback with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("feedback with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCoaching/{userid}/{activityid}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeedback(@PathVariable("userid") String userid, @PathVariable("activityid") String activityid) {
        logger.info("Fetching Feedback with activityid {}", activityid);
        
        Feedback feedback = new Feedback();
        
        CoachingActivities coachXtvt = new CoachingActivities();
        if(activityid!=null && !"".equals(activityid)) {
        	coachXtvt = coachingActivitiesService.findById(activityid);
        	
        	if(coachXtvt!=null) {
        		String coachingId = coachXtvt.getCoaching().getId();
        		
        		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        		JwtUser currentUser = (JwtUser) auth.getPrincipal();
        		String currentUserId = currentUser.getId();
        		
        		if(coachingId!=null && !"".equals(coachingId)) {
        			
        			//user give feedback for coach and coaching
        			if(currentUserId.equals(userid)) {
        				boolean isUser = coachingUserService.existsByCoaching_IdAndUser_Id(coachingId, userid);
            			if(isUser) {
            				feedback = feedbackService.findByType("PBPC");
            			}
        			}else {//coach feedback for other user
        				boolean isCoach = coachingCoachService.existsByCoaching_IdAndCoach_Id(coachingId, currentUserId);
        				if(isCoach) {
        					feedback = feedbackService.findByType("PBCP");
        				}
        			}
        			
        			feedback.setInstanceid(coachingId);
        		}else {
            		logger.error("feedback with coachingId {} not found.", coachingId);
                    return new ResponseEntity(new CustomErrorType("feedback with coachingId " + coachingId 
                            + " not found"), HttpStatus.NOT_FOUND);
            	}
        		
        	}else {
        		logger.error("feedback with activityid {} not found.", activityid);
                return new ResponseEntity(new CustomErrorType("feedback with id " + activityid 
                        + " not found"), HttpStatus.NOT_FOUND);
        	}
        }else {
    		logger.error("feedback with activityid {} not found.", activityid);
            return new ResponseEntity(new CustomErrorType("feedback with id " + activityid 
                    + " not found"), HttpStatus.NOT_FOUND);
    	}
        
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCapab/{userid}/{activityid}", method = RequestMethod.GET)
    public ResponseEntity<?> getCapabFeedback(@PathVariable("userid") String userid, @PathVariable("activityid") String activityid) {
        logger.info("Fetching Feedback with activityid {}", activityid);
        
        Feedback feedback = new Feedback();
        
        CapabilityActivities coachXtvt = new CapabilityActivities();
        if(activityid!=null && !"".equals(activityid)) {
        	coachXtvt = capabilityActivitiesService.findById(activityid);
        	
        	if(coachXtvt!=null) {
        		String capabId = coachXtvt.getCapability().getId();
        		
        		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        		JwtUser currentUser = (JwtUser) auth.getPrincipal();
        		String currentUserId = currentUser.getId();
        		
        		if(capabId!=null && !"".equals(capabId)) {
        			
        			//user give feedback for coach and coaching
        			if(currentUserId.equals(userid)) {
        				boolean isUser = capabilityUserService.existsByCapability_IdAndUser_Id(capabId, userid);
            			if(isUser) {
            				feedback = feedbackService.findByType("PBPC");
            			}
        			}else {//coach feedback for other user
        				boolean isCoach = capabilityCoachService.existsByCapability_IdAndCoach_Id(capabId, currentUserId);
        				if(isCoach) {
        					feedback = feedbackService.findByType("PBCP");
        				}
        			}
        			
        		}else {
            		logger.error("feedback with coachingId {} not found.", capabId);
                    return new ResponseEntity(new CustomErrorType("feedback with capabId " + capabId 
                            + " not found"), HttpStatus.NOT_FOUND);
            	}
        		
        	}else {
        		logger.error("feedback with activityid {} not found.", activityid);
                return new ResponseEntity(new CustomErrorType("feedback with capabId " + activityid 
                        + " not found"), HttpStatus.NOT_FOUND);
        	}
        }else {
    		logger.error("feedback with activityid {} not found.", activityid);
            return new ResponseEntity(new CustomErrorType("feedback with capabId " + activityid 
                    + " not found"), HttpStatus.NOT_FOUND);
    	}
        
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/training", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedbackTraining(@RequestBody TrainingTx trainingTx) {
        logger.info("Fetching TrainingTx with id {}", trainingTx.getId());
        
        Feedback feedback = new Feedback();
        String trainingId = trainingTx.getId();
        String mode = trainingTx.getStatus();//temp for mode
        String userid = trainingTx.getQualification();//temp for userid
        
        TrainingTx tx = new TrainingTx();
        if(trainingId!=null && !"".equals(trainingId)) {
        		
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			JwtUser currentUser = (JwtUser) auth.getPrincipal();
			String currentUserId = currentUser.getId();
			
			if(trainingId != null && !"".equals(trainingId)) {
				
				boolean isCoach = trainingCoachService.existsByTraining_IdAndCoach_Id(trainingId, currentUserId);
				if(isCoach) {
					feedback = feedbackService.findByType("PKK");
				}else {
					if(currentUserId.equals(userid)) {
						feedback = feedbackService.findByType(mode);
	    			}
				}
				
			}else {
	    		logger.error("feedback with TrainingTx {} not found.", trainingId);
	            return new ResponseEntity(new CustomErrorType("feedback with Training " + trainingId 
	                    + " not found"), HttpStatus.NOT_FOUND);
	    	}
			
        }else {
    		logger.error("feedback with TrainingTx {} not found.", trainingId);
            return new ResponseEntity(new CustomErrorType("feedback with Training " + trainingId 
                    + " not found"), HttpStatus.NOT_FOUND);
    	}
        
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<?> createFeedbackUser(@RequestBody FeedbackCollection feedbackCollection, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User Feedback : {}", feedbackCollection);
        
        Feedback feedback = new Feedback();
        //FeedbackSection[] answerArr = feedbackCollection.getFeedbackSection();
        FeedbackSectionQuestion[] answerQueArr = feedbackCollection.getFeedbackSectionQuestion();
        
        UserFeedback uf = new UserFeedback();
        List<UserFeedbackSection> usecLs = new ArrayList<UserFeedbackSection>();
        List<UserFeedbackSectionQuestion> userQueLs = new ArrayList<UserFeedbackSectionQuestion>();
        
        Coaching coaching = new Coaching();
        
        try {
        	
        	feedback = feedbackCollection.getFeedback();
        	String id = feedback.getId();
        	Feedback fbDb = feedbackService.findById(id);
        	
        	if(feedback != null) {
        		
        		String traxId = UUIDGenerator.getInstance().getId();
        		User user = new User();
        		String parentId = "";
        		String parentName = "";
        		String instanceId = "";
        		double totalScore = 0;
        		double totalAnsScore = 0;
        		String mode = feedbackCollection.getType();
        		
	    		List<FeedbackSection> secArr = new ArrayList<FeedbackSection>();
	    		secArr = feedback.getFeedbackSection();
        		
        		uf.setId(UUIDGenerator.getInstance().getId());
        		uf.setObjective(feedback.getObjective());
        		uf.setTitle(feedback.getTitle());
        		uf.setType(feedback.getType());
        		uf.setFeedbacktraxid(traxId);
        		
        		//save feedback into userFeedback
        		for(FeedbackSection section: secArr) {
        			
        			List<FeedbackSectionQuestion> fbQueLs = new ArrayList<FeedbackSectionQuestion>();
        			fbQueLs = section.getFeedbackSectionQuestion();
        			
        			UserFeedbackSection obj = new UserFeedbackSection();
        			obj.setId(UUIDGenerator.getInstance().getId());
        			obj.setTitle(section.getTitle());
        			obj.setUserFeedback(uf);
        			
        			usecLs.add(obj);
        			
        			for(FeedbackSectionQuestion queObj: fbQueLs) {
        				
        				String queId = queObj.getId();
        				String detail = "";
        				int score = 0; 
        				String type = queObj.getType();
        				int totalEach = 0;
        				
        				if("objektif".equals(type) || "subjobj".equals(type)) {
        					int min = queObj.getMin();
        					int max = queObj.getMax();
        					
        					for(int i = min; i<max+1; i++) {
        						totalEach = totalEach + i;
        					}
        				}
        				totalScore = totalScore + totalEach;
        				
        				//get user feedback answer from angular
        				for(FeedbackSectionQuestion ans: answerQueArr) {
        					String ansId = ans.getId();
            				instanceId = ans.getTitle();
            				parentId = ans.getType();
            						
        					if(ansId.equals(queId)) {
        						detail = ans.getDetail();
        						score = ans.getMax();
        					}
        				}
        				totalAnsScore = totalAnsScore + score;
        				
        				UserFeedbackSectionQuestion ufqueObj = new UserFeedbackSectionQuestion();
        				ufqueObj.setId(UUIDGenerator.getInstance().getId());
        				ufqueObj.setDetail(detail);
        				ufqueObj.setMax(queObj.getMax());
        				ufqueObj.setMaxlbl(queObj.getMaxlbl());
        				ufqueObj.setMin(queObj.getMin());
        				ufqueObj.setMinlbl(queObj.getMinlbl());
        				ufqueObj.setScore(score);
        				ufqueObj.setTitle(queObj.getTitle());
        				ufqueObj.setType(queObj.getType());
        				ufqueObj.setUserFeedbackSection(obj);
        				
        				userQueLs.add(ufqueObj);
        			}
        		}
        		
        		if("COACHING".equals(mode)) {
        			CoachingActivities caObj = coachingActivitiesService.findById(instanceId);
            		if(caObj!=null) {
            			parentName = caObj.getCoaching().getName();
            			parentId = caObj.getCoaching().getId();
            		}
        		}else if("TRAINING".equals(mode)) {
        			Training trainingObj = trainingService.findById(parentId);
        			if(trainingObj!=null) {
        				parentName = trainingObj.getTitle();
        			}
        			
        			TrainingTx txObj = trainingTxService.findByTraining_IdAndUser_IdAndStatusNotAndStatusNot(parentId, feedbackCollection.getUser().getId(), "4", "5");
        			if(txObj!=null) {
        				instanceId = txObj.getId();
        			}
        		}else if("CERTIFICATE".equals(mode)) {
        			
        		}else if("CAPABILITY".equals(mode)) {
        			CapabilityActivities caObj = capabilityActivitiesService.findById(instanceId);
            		if(caObj!=null) {
            			parentName = caObj.getCapability().getName();
            			parentId = caObj.getCapability().getId();
            		}
        		}
        		
        		uf.setMarks(totalAnsScore/totalScore * 100);
        		
        		UserFeedbackTrax traxObj = new UserFeedbackTrax();
        		traxObj.setId(traxId);
        		traxObj.setInstanceid(instanceId);
        		traxObj.setParentid(parentId);
        		traxObj.setType(mode);
        		traxObj.setTitle(parentName);
        		traxObj.setFeedbackname(feedback.getTitle());
        		traxObj.setUser(feedbackCollection.getUser());
        		traxObj.setCreatedon(new Date());
        		traxObj.setCreatedby(feedbackCollection.getCreatedby());
        		
        		//save all feedback
        		userFeedbackTraxService.save(traxObj);
        		userFeedbackService.save(uf);
        		userFeedbackSectionService.save(usecLs);
        		userFeedbackSectionQuestionService.save(userQueLs);
        		
        	}else {
        		logger.error("feedback with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("feedback with id " + id 
                        + " not found"), HttpStatus.NOT_FOUND);
        	}
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/status/{coachingid}/{activityid}/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getStatusFeedback(@PathVariable("userid") String userid, @PathVariable("coachingid") String coachingid, @PathVariable("activityid") String activityid) {
        logger.info("Fetching Feedback Status with coachingid {}", coachingid);
        
        boolean isExist = false;
        
        try {
        	isExist = userFeedbackTraxService.existsByParentidAndInstanceidAndUser_Id(coachingid, activityid, userid);
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        return new ResponseEntity<Boolean>(isExist, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getTrax", method = RequestMethod.POST)
    public ResponseEntity<?> getFeedbackTrax(@RequestBody UserFeedbackTrax userFeedbackTrax) {
        logger.info("Fetching Feedback Status with type {}", userFeedbackTrax.getType());
        
        UserFeedback ufb = new UserFeedback();
        
        String type = userFeedbackTrax.getType();
        String parentId = userFeedbackTrax.getParentid();
        String instanceId = userFeedbackTrax.getInstanceid();
        String userId = "";
        if(userFeedbackTrax.getUser()!=null) {
        	userId = userFeedbackTrax.getUser().getId();
        }
        
        try {
        	
        	/*if("COACHING".equals(type) || "CAPABILITY".equals(type)) {
        		
        		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        		JwtUser currentUser = (JwtUser) auth.getPrincipal();
        		String currentUserId = currentUser.getId();
        		
        		List<UserFeedbackTrax> lsTrax = new ArrayList<UserFeedbackTrax>();
        		
        		//user
        		if(currentUserId.equals(userId)) {
        			lsTrax = userFeedbackTraxService.findByParentidAndInstanceidAndUser_IdAndType
                			(parentId, instanceId, userId, type);
        			
        			for(UserFeedbackTrax trax: lsTrax) {
        				String id = trax.getId();
        				
        			}
        			
        		}else {
        			
        		}
            	
            	if(trax != null) {
            		ufb = userFeedbackService.findByFeedbacktraxid(trax.getId());
            	}
        		
        	}else if("TRAINING".equals(type)) {
        		if(parentId!=null && !"".equals(parentId)) {
        			ufb = userFeedbackService.findByFeedbacktraxid(parentId);
        		}
        	}*/
        	if(parentId!=null && !"".equals(parentId)) {
    			ufb = userFeedbackService.findByFeedbacktraxid(parentId);
    		}
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        return new ResponseEntity<UserFeedback>(ufb, HttpStatus.OK);
    }
}
