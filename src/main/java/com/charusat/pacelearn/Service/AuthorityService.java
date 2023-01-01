package com.charusat.pacelearn.Service;

import com.charusat.pacelearn.Entity.Authority;
import com.charusat.pacelearn.Repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;
    public Authority createNewAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }
}
