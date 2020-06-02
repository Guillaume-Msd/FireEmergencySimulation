package pk.cloudserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pk.cloudserver.model.ProbDataEntity;
import pk.cloudserver.service.CloudServerService;

@RestController
public class CloudServerController {
	@Autowired
	private CloudServerService cloudServ;
	
	@GetMapping(value="CloudServer/add", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void addEntity(@RequestBody ProbDataEntity prob) {
		cloudServ.addEntity(prob);
	}
	
	@GetMapping("CloudServer/remove")
	public void removeEntity() {
		cloudServ.removeEntity();
	}

}
