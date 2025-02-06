package com.hutech.organizations.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.organizations.dto.response.ApiResponse;
import com.hutech.organizations.dto.response.OrganizationDetails;
import com.hutech.organizations.entity.Organization;
import com.hutech.organizations.service.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationControllers {

	@Autowired
	private OrganizationService organizationService;

	// Create Organization
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<OrganizationDetails>> createOrganization(@RequestBody Organization organization) {
		ApiResponse<OrganizationDetails> response = organizationService.createOrganization(organization);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getall")
	public List<Organization> getAllOrganizations() {
		return organizationService.getAllOrganizations();
	}

	@GetMapping("/org/{id}")
	public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {
		Optional<Organization> organization = organizationService.getOrganizationById(id);
		return organization.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/orgId/{orgId}")
	public ResponseEntity<Organization> getOrganizationByOrgId(@PathVariable String orgId) {
		Optional<Organization> organization = organizationService.getOrganizationByOrgId(orgId);
		return organization.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	 @DeleteMapping("/org/{id}")
	    public ResponseEntity<String> softDeleteOrganization(@PathVariable String id) {
	        boolean deleted = organizationService.deactivateOrganization(id);
	        
	        if (deleted) {
	            return ResponseEntity.ok("Organization deactivated successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
	        }
	    }
	 
	 @PutMapping("/org/{id}")
	    public ResponseEntity<Map<String, Object>> updateOrganization(@PathVariable String id, @RequestBody Organization updatedOrganization) {
	        Optional<Organization> existingOrgOpt = organizationService.getOrganizationById(id);

	        Map<String, Object> response = new HashMap<>();
	        if (existingOrgOpt.isPresent()) {
	            Organization updatedOrg = organizationService.updateOrganization(id, updatedOrganization);

	            response.put("status", "organization updated");
	            response.put("code", "Success");
	            response.put("organization details", updatedOrg);
	            
	            return ResponseEntity.ok(response);
	        } else {
	            response.put("status", "organization not found");
	            response.put("code", "Failure");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
}
