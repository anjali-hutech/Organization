package com.hutech.organizations.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	
	
    private String status;
    private String code;
    private T organizationDetails;
    
    
}