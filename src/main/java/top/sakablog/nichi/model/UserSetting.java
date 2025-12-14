package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * UserSetting Entity
 * <p>
 * 存储用户的个性化设置。
 * TODO: 还未编辑完成
 * </p>
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @since 1.0.0
 * @version 1.0.1
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="word")
public class UserSetting {
    // 设置ID
    @Id
    @Column(name="setting_id")
    private Integer id;

    // 关联用户表
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
