package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.sakablog.nichi.model.UserProfile;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserInfoRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
