package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    List<UserAuth> findByUserId(Long userId);

    Optional<UserAuth> findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);

    boolean existsByIdentifier(String identifier);

    boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);
}
