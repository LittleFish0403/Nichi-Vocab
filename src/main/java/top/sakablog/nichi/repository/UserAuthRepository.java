package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * UserAuthRepository
 * </p>
 * UserAuth 的数据访问层接口，提供对用户认证信息的 CRUD 操作。
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    List<UserAuth> findByUserId(Long userId);

    Optional<UserAuth> findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);

    boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);
}
