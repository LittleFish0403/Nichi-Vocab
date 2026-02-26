package top.sakablog.nichi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.sakablog.nichi.model.UserProfile;

/**
 * <p>
 * UserProfileRepository
 * </p>
 * UserProfile的数据访问层接口，提供对UserProfile实体的CRUD操作
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    // 根据用户ID查找用户资料
    UserProfile findByUserId(Long userId);

    // 根据用户ID检查用户资料是否存在
    boolean existsByUserId(Long userId);
}
