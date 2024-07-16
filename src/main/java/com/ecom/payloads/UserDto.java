package com.ecom.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4,message = "Name must be minimum of 4 characters !!")
    private String name;
    @Email(message = "Email address is not valid !!")
    private String email;
    @NotEmpty
    @Size(min = 4,max = 10,message = "Password must be min of 4 char & max of 10 !!")
    private String password;
    @NotEmpty
    private String phoneNo;
    private String userPic;
    private String address;
    private Set<RoleDto> roles = new HashSet<>();
}
