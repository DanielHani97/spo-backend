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

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.user.model.AppAuthority;
import my.com.fotia.osdec.user.model.Authority;
import my.com.fotia.osdec.user.model.UserAuthorities;
import my.com.fotia.osdec.user.service.AppAuthorityService;
import my.com.fotia.osdec.user.service.AuthorityService;
import my.com.fotia.osdec.user.service.UserAuthoritiesService;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.GlobalStatic;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	public static final Logger logger = LoggerFactory.getLogger(RefController.class);
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	AppAuthorityService appAuthorityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserAuthoritiesService userAuthoritiesService;
	
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody AppAuthority app, UriComponentsBuilder ucBuilder) {
        logger.info("Inserting Auhtority Application : {}", app);
        
        try {
        	String roleId = app.getRoleid();
        	
        	if(roleId !=null && !"".equals(roleId)) {
        		
        		if(roleId.equals(GlobalStatic.ROLE_ADMIN)) {
        			roleId = GlobalStatic.ROLE_ADMIN_ID;
        		}else if(roleId.equals(GlobalStatic.ROLE_COACH)) {
        			roleId = GlobalStatic.ROLE_COACH_ID;
        		}else if(roleId.equals(GlobalStatic.ROLE_SUPERVISOR)) {
        			roleId = GlobalStatic.ROLE_SUPERVISOR_ID;
        		}
        		
                Authority authority = authorityService.findById(roleId);
                
                if(authority != null) {
                	app.setId(UUIDGenerator.getInstance().getId());
                    app.setCreatedby(app.getUser());
                    app.setCreatedon(new Date());
                    app.setUser(app.getUser());
                    app.setAuthority(authority);
                    
                    appAuthorityService.save(app);
                }
        	}
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/role/create/{id}").buildAndExpand(app.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody AppAuthority app) {
        logger.info("Updating AppAuthority with id {}", id);
        
        AppAuthority obj = new AppAuthority();
        
        try {
        	
        	obj = appAuthorityService.findById(id);
        	if (obj == null) {
                logger.error("Unable to update. AppAuthority with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("Unable to upate. AppAuthority with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }else {
            	
            	String status = app.getStatus();
            	
            	obj.setAdminremarks(app.getAdminremarks());
            	obj.setStatus(status);
            	obj.setModifiedby(app.getUser());
            	obj.setModifiedon(new Date());
            	
            	appAuthorityService.save(obj);
            	
            	if("APPROVE".equals(status)) {
            		
            		UserAuthorities ua = new UserAuthorities();
            		ua.setId(UUIDGenerator.getInstance().getId());
            		ua.setCreatedby(app.getUser().getId());
            		ua.setCreatedon(new Date());
            		ua.setUser(obj.getUser());
            		ua.setAuthority(obj.getAuthority());
            		
            		userAuthoritiesService.save(ua);
            	}
            }
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	logger.error("Unable to update. AppAuthority with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. AppAuthority with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        return new ResponseEntity<AppAuthority>(obj, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json")
    public String appList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<AppAuthority> ls = new ArrayList<AppAuthority>();
		
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
			
			ls = appAuthorityService.
					findByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = appAuthorityService.
					countByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(search, search, search);
			
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
	
	//							AUDIT LIST
	@RequestMapping(value = "/listAudit", method = RequestMethod.POST, produces = "application/json")
    public String appListAudit(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<AppAuthority> ls = new ArrayList<AppAuthority>();
		
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
			
			String status = "NEW";
			
			ls = appAuthorityService.
					findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase
					(status, search, status, search, status, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = appAuthorityService.
					countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase
					(status, search, status, search, status, search);
			
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
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAppAuhtority(@PathVariable("id") String id) {
        logger.info("Fetching agency with id {}", id);
        AppAuthority obj = appAuthorityService.findById(id);
        
        if(obj != null) {
        	obj.setRoleid(obj.getAuthority().getRolename());
        }else{
            logger.error("AppAuthority with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("AppAuthority with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AppAuthority>(obj, HttpStatus.OK);
    }
}
