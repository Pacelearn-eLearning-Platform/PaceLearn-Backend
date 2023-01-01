package com.charusat.pacelearn.Repository;

import com.charusat.pacelearn.Entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority,String> {
}
