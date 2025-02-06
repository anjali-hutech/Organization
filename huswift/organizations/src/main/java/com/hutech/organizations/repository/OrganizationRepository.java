package com.hutech.organizations.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hutech.organizations.entity.Organization;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {

	
	Optional<Organization> findByBasicInfoOrgId(String orgId);
}
