package com.charusat.pacelearn.repository;

import com.charusat.pacelearn.domain.Authority;
import com.charusat.pacelearn.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    /**
     * CUSTOM
     * */
    @Query(value = "select count(*) from jhi_user_authority where authority_name =:authority", nativeQuery = true)
    Integer countAllByAuthoritiesContains(@Param("authority") String authority);

    @Query(value = "select * from jhi_user where id in (SELECT user_id FROM jhi_user_authority where authority_name =:authority and user_id not in (SELECT user_id FROM jhi_user_authority where authority_name =:authority_reviewer))", nativeQuery = true)
    List<User> findAllByAuthorities(@Param("authority") String authority,@Param("authority_reviewer") String authority_reviewer);

    @Query(value = "select * from jhi_user where id in (SELECT user_id FROM jhi_user_authority where authority_name =:authority)", nativeQuery = true)
    List<User> findAllByAuthoritiesReviewer(@Param("authority") String authority);
}
