package com.hutech.organizations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfo {

	  private String organizationName;
      private String companyRegistrationNumber;
      private String type;
      private String industry;
      private String phone;
      private String email;
      private String website;
      private String orgId; 
      private boolean active = true;  

      private Address address;
	    
}
