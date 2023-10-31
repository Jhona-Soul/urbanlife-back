package com.urbanlife.urbanlife.models.usuario.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Modifying
    @Query(value = """
                select t.* from urbanlife.token t inner join urbanlife.usuario as u
                on t.user_id = u.id_usuario
                where u.id_usuario =:id  and (t.expired = false or t.revoked = false)
            """, nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);
    Optional<Token> findByToken(String token);
}
