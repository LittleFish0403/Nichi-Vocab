package top.sakablog.nichi.model.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;

import java.util.List;

/**
 * User Entity
 * <p>
 * 存储用户的基本信息。
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.1.1
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "users")
public class User {
    // 用户ID
    @Id
    @NotNull
    @Column(name="user_id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    // 用户名
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // 头像URL
    @Column(length = 100)
    private String avatarUrl;

    // 关联表
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAuth> userAuths;
}
