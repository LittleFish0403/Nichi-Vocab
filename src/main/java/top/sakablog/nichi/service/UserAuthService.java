package top.sakablog.nichi.service;

import top.sakablog.nichi.model.entity.user.UserAuth;
import top.sakablog.nichi.model.enums.IdentityType;

import java.util.List;

public interface UserAuthService {
    UserAuth createUserAuth(Long userId, IdentityType identityType, String identifier, String credential);

    List<UserAuth> findByUserId(Long userId);

    UserAuth findByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);

    boolean existsByIdentifier(String identifier);

    boolean existsByIdentityTypeAndIdentifier(IdentityType identityType, String identifier);
}
