package com.charusat.pacelearn.Controller;


import com.charusat.pacelearn.Entity.Authority;
import com.charusat.pacelearn.Service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

    @PostMapping({"/createNewAuthority"})
    public Authority createNewAuthority(@RequestBody Authority authority) {
        return authorityService.createNewAuthority(authority);
    }
}
