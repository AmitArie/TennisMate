package com.tennismate.tennismate.user;

import java.util.Date;

enum Gender {
    MALE, FEMALE
}

public class User
{

    private String username;
    private String email;
    private String password; // maybe not needed. FB / GOOGLE AUTH
    private Date dateOfBirth;
    private Gender gender;



}
