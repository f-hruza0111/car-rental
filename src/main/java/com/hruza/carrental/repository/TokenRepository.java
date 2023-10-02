package com.hruza.carrental.repository;

import com.hruza.carrental.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            SELECT t from Token t inner join AppUser u on t.user.id = u.id  
            WHERE u.id = :userID
            AND (t.expired = false and t.revoked = false)
            """)
    List<Token> findAllValidTokensByUser(Long userID);

    @Query("""
            SELECT t from Token t inner join AppUser u on t.user.id = u.id  
            WHERE u.id = :userID
            AND (t.expired = false and t.revoked = false)
            AND t.tokenType = 'ACCESS'
            """)
    List<Token> findAllValidAccessTokensByUser(Long userID);

//    @Query("""
//            DELETE t from Token
//            WHERE t.user.id = :userID
//            """)
//    void deleteAllTokensWithUserID(Long userID);

    Optional<Token> findByToken(String token);
}
