package com.charusat.pacelearn.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {

    @Id
    private String AuthorityName;
    private String AuthorityDescription;

    public String getAuthorityName() {
        return AuthorityName;
    }

    public void setAuthorityName(String AuthorityName) {
        this.AuthorityName = AuthorityName;
    }

    public String getAuthorityDescription() {
        return AuthorityDescription;
    }

    public void setAuthorityDescription(String AuthorityDescription) {
        this.AuthorityDescription = AuthorityDescription;
    }
}
