package my.com.fotia.osdec.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.general.agency.service.CompanyService;
import my.com.fotia.osdec.general.certificate.model.Certificate;
import my.com.fotia.osdec.general.certificate.service.CertificateService;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.references.service.FilestorageService;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.model.UserAuthorities;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.user.service.UserAuthoritiesService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.GlobalFunction;
import my.com.fotia.osdec.utilities.GlobalStatic;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	UserAuthoritiesService userAuthoritiesService;
	@Autowired
	CompanyService companyService;
	@Autowired
	CertificateService certificateService;
	@Autowired
	FilestorageService filestorageService;
	@Autowired
    private EmailController emailController;

	@RequestMapping(value = "/user/getall", method = RequestMethod.GET)
	public ResponseEntity<List<User>> userList() {
		List<User> list = userService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	@RequestMapping(value = "/usergetall", method = RequestMethod.POST, produces = "application/json")
    public String userListing() throws Exception {
		
		String result = "";
		
		List<User> ls = new ArrayList<User>();
		
		try {
			ls = userService.userList();
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
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
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		
        return result;

	}
	
	@RequestMapping(value = "/usergetuser", method = RequestMethod.POST, produces = "application/json")
    public String userUserListing(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search,
    		@RequestParam (value= "datatable[userLs][]",required=false) String[] userLs
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		Collection <String> exceptId = new ArrayList<String>();
		
		List<String> id = new ArrayList<String>();
		if(userLs!=null) {
			for(int i=0; i<userLs.length; i++ ) {
				id.add(userLs[i]);
			}
			exceptId = new ArrayList<String>(id);
		}else {
			id = null;
		}
		
		List<UserAuthorities> ls = new ArrayList<UserAuthorities> ();
		try {
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
			}else if(sortField.equals("Email")) {
				sortField = "User_Email";
			}

			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			if (userLs==null) {
				ls = userAuthoritiesService.findByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase("SECURITY-AUTHORITY-USER", search, "SECURITY-AUTHORITY-USER", search, pageable);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = userAuthoritiesService.countByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase("SECURITY-AUTHORITY-USER", search, "SECURITY-AUTHORITY-USER", search);
			
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
				
			}else {
				ls = userAuthoritiesService.findByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUserIdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId, "SECURITY-AUTHORITY-USER", search, exceptId, "SECURITY-AUTHORITY-USER", search, pageable);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = userAuthoritiesService.countByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUser_IdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId,"SECURITY-AUTHORITY-USER", search, exceptId,"SECURITY-AUTHORITY-USER", search);
				
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
			}
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
        return result;

	}
	@RequestMapping(value = "/user/getallcoach", method = RequestMethod.GET)
	public String coachList() throws Exception {
		List<UserAuthorities> list = userAuthoritiesService.findByAuthority_Id("SECURITY-AUTHORITY-COACH");
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(list);
		return jsonRes;
	}
	
	@RequestMapping(value = "/usergetcoach", method = RequestMethod.POST, produces = "application/json")
    public String userCoachListing(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search,
    		@RequestParam (value= "datatable[coachLs][]",required=false) String[] coachLs
    		
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		Collection <String> exceptId = new ArrayList<String>();
		
		List<String> id = new ArrayList<String>();
		if(coachLs!=null) {
			for(int i=0; i<coachLs.length; i++ ) {
				id.add(coachLs[i]);
			}
			exceptId = new ArrayList<String>(id);
		}else {
			id = null;
		}
		
		
		List<UserAuthorities> ls = new ArrayList<UserAuthorities> ();
		try {
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
			}else if(sortField.equals("email")) {
				sortField = "User_Email";
			}

			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			if (coachLs==null) {
				ls = userAuthoritiesService.findByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase("SECURITY-AUTHORITY-COACH", search, "SECURITY-AUTHORITY-COACH", search, pageable);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = userAuthoritiesService.countByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase("SECURITY-AUTHORITY-COACH", search, "SECURITY-AUTHORITY-COACH", search);
			
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
				
			}else {
				ls = userAuthoritiesService.findByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUserIdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId, "SECURITY-AUTHORITY-COACH", search, exceptId, "SECURITY-AUTHORITY-COACH", search, pageable);
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = userAuthoritiesService.countByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUser_IdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId,"SECURITY-AUTHORITY-COACH", search, exceptId,"SECURITY-AUTHORITY-COACH", search);
				
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
			}
			
			
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
        return result;

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        logger.info("Fetching user with id {}", id);
        User user = userService.findById(id);
        if (user == null) {
            logger.error("user with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating user : {}", user);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        user.setId(UUIDGenerator.getInstance().getId());
        
        userService.save(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/profile/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProfile(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
        
        User currentUser = userService.findById(id);
        
        
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        		
        		
			currentUser.setUsername(user.getUsername());
			currentUser.setName(user.getName());
			currentUser.setEmail(user.getEmail());
			currentUser.setPhoneNo(user.getPhoneNo());        			
			currentUser.setPosition(user.getPosition());
			currentUser.setGrade(user.getGrade());
			currentUser.setSchema(user.getSchema());
			currentUser.setAddress(user.getAddress());
			currentUser.setState(user.getState());
			currentUser.setCity(user.getCity());
			currentUser.setPostcode(user.getPostcode());
			currentUser.setCreated_by(user.getCreated_by());
			currentUser.setModified_by(user.getModified_by());
			currentUser.setCreated_date(user.getCreated_date());
			currentUser.setModified_date(user.getModified_date());
			
			userService.save(currentUser);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        }
        
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/agency/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserAgency(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        User currentUser = userService.findById(id);
        
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        currentUser.setType(user.getType());
        
        String type = user.getType();
		if("GOV".equals(type)) {
			currentUser.setAgency(user.getAgency());
			
		}else {
			Company obj = new Company();
			obj = user.getCompany();
			if(obj!=null) {
				String idObj = obj.getId();
				if(idObj ==null || "".equals(idObj)) {
					idObj = UUIDGenerator.getInstance().getId();
					obj.setId(idObj);
				}
				companyService.save(obj);
			}
			
			currentUser.setCompany(obj);
			
		}
        		
		userService.save(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
        			
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/skill/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserSkill(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        //Agency currentAgency = agencyService.findById(id);
        User currentUser = new User();
        if(id !=null && !"".equals(id)) {
        		currentUser = userService.findById(id);
        		if(currentUser != null) { 
        			currentUser.setSkill(user.getSkill());
        			
        			userService.save(currentUser);
        			
        } else  {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/project/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProject(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        //Agency currentAgency = agencyService.findById(id);
        User currentUser = new User();
        
        if(id !=null && !"".equals(id)) {
        		currentUser = userService.findById(id);
        		if(currentUser != null) { 
        			currentUser.setProjectLs(user.getProjectLs());
        			
        			userService.save(currentUser);
        	
        			
        		
        } else  {
            logger.error("Unable to update. Project with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Project with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        
        
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/cert/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserCert(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        //Agency currentAgency = agencyService.findById(id);
        User currentUser = new User();
        if(id !=null && !"".equals(id)) {
        		currentUser = userService.findById(id);
        		if(currentUser != null) { 
        			//currentUser.setCertificate(user.getCertificate());
        			
        			userService.save(currentUser);
        	
        			
        		
        } else  {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        
        
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/setting/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserSetting(@PathVariable("id") String id,  @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        User currentUser = new User();
        if(id !=null && !"".equals(id)) {
        	currentUser = userService.findById(id);
        		if(currentUser != null) {
        			
        			String password = user.getOld_password();
        			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        			String hashedPassword = passwordEncoder.encode(password);
        		
        			currentUser.setOld_password(hashedPassword);
        			
        			userService.save(currentUser);
        		} 
        }
        
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/user/list", method = RequestMethod.POST, produces = "application/json")
    public String userList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<User> ls = new ArrayList<User>();
		
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
			
			ls = userService.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase
					(search, search, search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = userService.countByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrAgency_NameContainingIgnoreCaseOrPositionContainingIgnoreCase
					(search, search, search, search, search);
			
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
	
	@RequestMapping(value = "/user/edit/picture", method = RequestMethod.POST, consumes="multipart/form-data")
    public void createTraining(@RequestPart("avatar") MultipartFile file, @RequestPart("info") String id, UriComponentsBuilder ucBuilder) {

        byte [] byteArr = null;
        
        User currentUser = new User();
        
        try {
        	
        	if(id != null) {
        		currentUser = userService.findById(id);
                try {
            		if (file.getSize() > 0) {
            			byteArr = file.getBytes();
        				// Byte[]
        				//String filename = file.getOriginalFilename();// Get attached
        				// logo name
            			currentUser.setImage(byteArr);
        			}
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                
                userService.save(currentUser);
        	}
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(currentUser.getId()).toUri());
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/edit/all/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
        
        User currentUser = userService.findById(id);
        
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        	
        String type = user.getType();
		if("GOV".equals(type)) {
			currentUser.setAgency(user.getAgency());
			
		}else {
			Company obj = new Company();
			obj = user.getCompany();
			if(obj!=null) {
				String idObj = obj.getId();
				if(idObj ==null || "".equals(idObj)) {
					idObj = UUIDGenerator.getInstance().getId();
					obj.setId(idObj);
				}
				companyService.save(obj);
			}
			
			currentUser.setCompany(obj);
		}
        		
		currentUser.setUsername(user.getUsername());
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhoneNo(user.getPhoneNo());        			
		currentUser.setPosition(user.getPosition());
		currentUser.setGrade(user.getGrade());
		currentUser.setSchema(user.getSchema());
		currentUser.setAddress(user.getAddress());
		currentUser.setState(user.getState());
		currentUser.setCity(user.getCity());
		currentUser.setPostcode(user.getPostcode());
		currentUser.setModified_by(user.getModified_by());
		currentUser.setModified_date(user.getModified_date());
		
		userService.save(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/user/application", method = RequestMethod.POST, produces = "application/json")
    public String userListRequest(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<User> ls = new ArrayList<User>();
		
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
			
			ls = userService.findByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled
					(search, false, search, false, search, false, search, false, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = userService.countByNameContainingIgnoreCaseAndEnabledOrUsernameContainingIgnoreCaseAndEnabledOrEmailContainingIgnoreCaseAndEnabledOrAgency_NameContainingIgnoreCaseAndEnabled
					(search, false, search, false, search, false, search, false);
			
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
	@RequestMapping(value = "/user/update/status/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> updateUserStatus(@PathVariable("id") String id) {
        logger.info("Fetching & update User with id {}", id);
 
        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to update user with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update user with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
    	String password = GlobalFunction.GeneratePassword(8);
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
        
        //Email properties
        String emailContent = String.format(GlobalStatic.EMAIL_NEW_ACCOUNT,new Object[]{user.getName(), user.getUsername(),password});
        String emailSubject = "Akaun Baru Sistem Pengurusan OSDeC";
        String emailTo = user.getEmail();
        
        user.setOld_password(hashedPassword);
    	user.setLastPasswordResetDate(new Date());
    	user.setEnabled(true);
        
        userService.save(user);
        
        //send email
        emailController.sendEmail(emailContent, emailSubject, emailTo, "");
        
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/user/update/certificate", method = RequestMethod.POST, consumes="multipart/form-data")
    public void createCert(@RequestPart("cert") MultipartFile file, @RequestPart("info") Certificate certificate, UriComponentsBuilder ucBuilder) {

        byte [] byteArr = null;
        Certificate cert = new Certificate();
        Filestorage storage = new Filestorage();
        
        try {
        	
        	if(certificate != null) {
        		String certId = UUIDGenerator.getInstance().getId();
                try {
            		if (file.getSize() > 0) {
            			byteArr = file.getBytes();
        				// Byte[]
        				//String filename = file.getOriginalFilename();// Get attached
        				// logo name
            			
            			storage.setId(UUIDGenerator.getInstance().getId());
            			storage.setType(file.getContentType());
            			storage.setName(file.getOriginalFilename());
            			storage.setContent(byteArr);
            			storage.setInstanceid(certId);
            			
            			cert.setFilestorage(storage);
        			}
            		
            		cert.setId(certId);
            		cert.setCategory(certificate.getCategory());
            		cert.setName(certificate.getName());
            		cert.setUser(certificate.getUser());
            		
            		filestorageService.save(storage);
            		certificateService.save(cert);
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
    }
}
