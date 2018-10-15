package my.com.fotia.osdec.controller;

import java.util.ArrayList;
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

import my.com.fotia.osdec.general.grade.model.Grade;
import my.com.fotia.osdec.general.project.model.Project;
import my.com.fotia.osdec.general.project.service.ProjectService;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class ProjectController {
	
	public static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	ProjectService projectService;

	
	
	@RequestMapping(value = "/project/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Project>> projectList() {
		List<Project> list = projectService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Project>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Project>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/project/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable("id") String id) {
        logger.info("Fetching project with id {}", id);
        Project project = projectService.findById(id);
        if (project == null) {
            logger.error("project with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Project with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/project/create", method = RequestMethod.POST)
    public ResponseEntity<?> createProject(@RequestBody Project project, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Project : {}", project);
 
        project.setId(UUIDGenerator.getInstance().getId());
        
        projectService.save(project);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(project.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/project/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProject(@PathVariable("id") String id, @RequestBody Project project) {
        logger.info("Updating Project with id {}", id);
 
        //Agency currentAgency = agencyService.findById(id);
        Project currentProject = projectService.findById(id);
        if(currentProject == null) {
        	logger.error("Unable to update. Project with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Project with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        			
        		
        } 
        
        	currentProject.setName(project.getName());
        	currentProject.setTechnology(project.getTechnology());
        	currentProject.setRole(project.getRole());
        	currentProject.setDuration(project.getDuration());
        	currentProject.setDescription(project.getDescription());
        	currentProject.setType(project.getType());
        	currentProject.setUser(project.getUser());    
        	currentProject.setAgency(project.getAgency());
        	
        	projectService.save(currentProject);
        	return new ResponseEntity<Project>(currentProject, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/project/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProject(@PathVariable("id")  String user_id) {
        logger.info("Fetching & Deleting Project with id {}", user_id);
        
//        projectService.deleteById(id);
        projectService.deleteByUserId(user_id);
        return new ResponseEntity<Project>(HttpStatus.NO_CONTENT);
    }
}
