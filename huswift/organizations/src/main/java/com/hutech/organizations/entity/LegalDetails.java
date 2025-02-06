package com.hutech.organizations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalDetails {
	
	 private String gstin;
     private String pan;
     private String incorporationDate;
     private String corporateIdentificationNumber;

}
