package com.droute.driverservice.dto;

import java.util.Set;

import com.droute.driverservice.entity.Role;
import com.droute.driverservice.enums.ProfileStatus;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserEntity {

	private Long userId;

	private String fullName;

	private String email;

	private String password;

	private Set<Role> roles;

	private String contactNo;

	private String colorHexValue;

	private ProfileStatus status; // Enum for ACTIVE, INACTIVE, BLOCKED, etc.

}
