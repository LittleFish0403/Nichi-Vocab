package top.sakablog.nichi.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.sakablog.nichi.model.entity.user.User;

/**
 * <p>
 * UserRepository
 * </p>
 * User的数据访问层接口，提供对User实体的CRUD操作
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查找用户
    User findByUsername(String username);

    // 根据邮箱查找用户
    boolean existsUserByUsername(String username);
}
