package my.com.fotia.osdec.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import my.com.fotia.osdec.controller.AgencyController;
import my.com.fotia.osdec.controller.EmailController;
import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.general.agency.service.CompanyService;
import my.com.fotia.osdec.security.JwtTokenUtil;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.training.service.TrainingService;
import my.com.fotia.osdec.user.model.Authority;
import my.com.fotia.osdec.user.model.AuthorityName;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.model.UserAuthorities;
import my.com.fotia.osdec.user.service.UserAuthoritiesService;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.GlobalFunction;
import my.com.fotia.osdec.utilities.GlobalStatic;
import my.com.fotia.osdec.utilities.UUIDGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class UserRestController {
	
	public static final Logger logger = LoggerFactory.getLogger(AgencyController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserAuthoritiesService userAuthoritiesService;
    
    @Autowired
    private TrainingService trainingService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private EmailController emailController;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
        
        if (userService.existsByUsername(user.getUsername())) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with username " + 
            user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }else {
        	
        	//password = user.getOld_password();
        	String password = GlobalFunction.GeneratePassword(8);
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		String hashedPassword = passwordEncoder.encode(password);
    		
    		//Email properties
            String emailContent = String.format(GlobalStatic.EMAIL_NEW_ACCOUNT,new Object[]{user.getName(), user.getUsername(),password});
            String emailSubject = "Akaun Baru Sistem Pengurusan OSDeC";
            String emailTo = user.getEmail();
        	
        	user.setId(UUIDGenerator.getInstance().getId());
        	
            //userService.save(user);
        	Authority auth = new Authority();
        	auth.setId("SECURITY-AUTHORITY-USER");
        	auth.setName(AuthorityName.ROLE_USER);
            
            //save default user authorities
            UserAuthorities obj = new UserAuthorities();
            obj.setId(UUIDGenerator.uuid.getId());
            obj.setUser(user);
            obj.setAuthority(auth);
            obj.setCreatedby(user.getId());
            obj.setCreatedon(new Date());
            user.setLastPasswordResetDate(new Date());
            
            //insert into agency or company
            String type = user.getType();
    		if("GOV".equals(type)) {
    			user.setOld_password(hashedPassword);
    			user.setAgency(user.getAgency());
    			user.setCompany(null);
    			user.setEnabled(true);
    			
    			userAuthoritiesService.save(obj);
                
    			//send email
                emailController.sendEmail(emailContent, emailSubject, emailTo, "");
    		}else {
    			user.setAgency(null);
    			Company objComp = new Company();
    			objComp = user.getCompany();
    			objComp.setId(UUIDGenerator.getInstance().getId());
    			user.setEnabled(false);
    			
    			companyService.save(objComp);
    			user.setCompany(objComp);
    			
    			userAuthoritiesService.save(obj);
    		}
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/auth/signup/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> reset(@RequestBody User inputUser, UriComponentsBuilder ucBuilder) {
        logger.info("Reset Password User email: {}", inputUser.getEmail());
        
        User user = new User();
        user = userService.findByEmail(inputUser.getEmail());
        
        if (user == null) {
            logger.error("Unable to reset password for user, email not exist");
            return new ResponseEntity(new CustomErrorType("Emel pengguna tidak wujud dalam sistem."),HttpStatus.CONFLICT);
        }else {
        	
        	String password = GlobalFunction.GeneratePassword(8);
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    		String hashedPassword = passwordEncoder.encode(password);
    		
    		user.setOld_password(hashedPassword);
    		user.setLastPasswordResetDate(new Date());
    		
    		userService.save(user);
            
            //SEND EMAIL
            String emailContent = String.format(GlobalStatic.EMAIL_RESET, new Object[]{password});
            String emailSubject = "Set Semula Kata Laluan Sistem Pengurusan OSDeC";
            String emailTo = user.getEmail();
            
            emailController.sendEmail(emailContent, emailSubject, emailTo, "");
            //END OF SEND EMAIL
        }
 
        return new ResponseEntity(new CustomErrorType("Emel telah berjaya dihantar"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/file", method = RequestMethod.POST, consumes="multipart/form-data")
    public void getTest(@RequestPart("avatar") MultipartFile file, @RequestPart("info") Training training) {
    	
    	String a = "";
    	a = "123123";
    	byte [] byteArr = null;
    	
    	Training obj = new Training();
    	
    	training.setId(UUIDGenerator.getInstance().getId());
        
        training.setCreated_by("IRA");
        training.setCreated_date(new Date());
        training.setModified_by("IRA");
        training.setModified_date(new Date());
        
        try {
    		if (file.getSize() > 0) {
    			byteArr = file.getBytes();
				// Byte[]
				//String filename = file.getOriginalFilename();// Get attached
				// logo name
				training.setImage(byteArr);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        trainingService.save(training);
    }
    
}
