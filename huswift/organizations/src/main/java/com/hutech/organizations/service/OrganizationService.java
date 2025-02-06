package com.hutech.organizations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hutech.organizations.dto.response.ApiResponse;
import com.hutech.organizations.dto.response.OrganizationDetails;
import com.hutech.organizations.entity.BasicInfo;
import com.hutech.organizations.entity.Organization;
import com.hutech.organizations.repository.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	public ApiResponse<OrganizationDetails> createOrganization(Organization organization) {

		organization.getBasicInfo().setOrgId(generateUniqueId());
		Organization savedOrganization = organizationRepository.save(organization);
		OrganizationDetails organizationDetails = new OrganizationDetails(savedOrganization.getBasicInfo().getOrgId());
		return new ApiResponse<>("Organization created", "Success", organizationDetails);
	}

	private String generateUniqueId() {
		return "HS_OR_" + String.format("%016d", (long) (Math.random() * 10000000000000000L));
	}

	public Optional<Organization> getOrganizationById(String id) {
		return organizationRepository.findById(id);
	}

	public List<Organization> getAllOrganizations() {
		return organizationRepository.findAll();
	}

	public Optional<Organization> getOrganizationByOrgId(String orgId) {
		return organizationRepository.findByBasicInfoOrgId(orgId);
	}

	public boolean deactivateOrganization(String id) {
		Optional<Organization> organizationOpt = organizationRepository.findById(id);

		if (organizationOpt.isPresent()) {
			Organization organization = organizationOpt.get();
			// Set the 'active' field inside BasicInfo to false
			organization.getBasicInfo().setActive(false);

			// Save the organization back to the database
			organizationRepository.save(organization);
			return true;
		}

		return false;
	}

	public Organization updateOrganization(String id, Organization updatedOrganization) {
		Optional<Organization> existingOrgOpt = organizationRepository.findById(id);

		if (existingOrgOpt.isPresent()) {
			Organization existingOrganization = existingOrgOpt.get();

			// Update fields of BasicInfo and other parts of the Organization object
			BasicInfo updatedBasicInfo = updatedOrganization.getBasicInfo();
			existingOrganization.setBasicInfo(updatedBasicInfo); // Update BasicInfo

			// Optionally update other fields like LegalDetails
			existingOrganization.setLegalDetails(updatedOrganization.getLegalDetails());

			// Save the updated organization object back to the database
			return organizationRepository.save(existingOrganization);
		}

		return null; // Return null if the organization is not found
	}
}
