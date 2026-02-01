package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * UserInfo Entity
 * <p>
 * 存储用户的基础信息
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.2
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="user_info")
public class UserInfo {
    // 设置ID
    @Id
    @Column(name="info_id")
    private Long id;

    // 用户名
    @Column(nullable = true, unique = true, length = 50)
    private String username;

    // 邮箱
    @Column(nullable = true, unique = true, length = 50)
    private String email;

    // 头像URL
    @Column(nullable = true, unique = true, length = 100)
    private String avatarUrl;

    // 关联用户表
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
