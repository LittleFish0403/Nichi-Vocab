package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * User Entity
 * <p>
 * 存储用户的基本信息。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "user")
public class User {
    // 用户ID
    @Id
    @Column(name="id", nullable = false, unique = true)
    @GeneratedValue
    private Long user_id;

    // 电话号码
    @Column(nullable = true, unique = true, length = 50)
    private String phone;

    // 密码
    @Column(nullable = false, length = 50)
    private String password;

    // 关联用户设置表
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserInfo userInfo;
}
