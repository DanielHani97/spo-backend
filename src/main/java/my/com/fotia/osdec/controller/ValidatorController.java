package my.com.fotia.osdec.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import my.com.fotia.osdec.utilities.GlobalStatic;

@RestController
@RequestMapping("/api/verify/")
public class ValidatorController {
	
	public static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);
	
	private Pattern pattern; 
	private Matcher matcher;
	
	String pswdPatter = GlobalStatic.PASSWORD_PATTERN;
	
	@RequestMapping(value = "/password", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> agencyList(String password) {
		
		boolean result = false;
		if(ValidatePattern(pswdPatter, password).matches()){
			result = true;
		}
		
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	public Matcher ValidatePattern(String patternObj,String matchObj){
		pattern = Pattern.compile(patternObj);  
		matcher = pattern.matcher(matchObj);  
		return matcher;
	}

}
