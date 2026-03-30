package top.sakablog.nichi.model.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.sakablog.nichi.config.Snowflake;
import top.sakablog.nichi.model.enums.IdentityType;

/**
 * UserAuth Entity
 * <p>
 * 存储用户登录认证信息，如手机号、邮箱和加密凭证。
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "user_auth")
public class UserAuth {
    @Id
    @NotNull
    @Column(name = "user_auth_id", nullable = false, unique = true)
    @Snowflake
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "identity_type", nullable = false, length = 20)
    private IdentityType identityType;

    @Column(name = "identifier", nullable = false, unique = true, length = 100)
    private String identifier;

    @Column(name = "credential", nullable = false, length = 255)
    private String credential;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
