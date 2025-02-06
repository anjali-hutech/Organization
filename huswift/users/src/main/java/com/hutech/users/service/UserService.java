package com.hutech.users.service;

import com.hutech.users.dto.CreateUserRole;
import com.hutech.users.entity.User;

public interface UserService {

	public CreateUserRole signup(User user);

}
