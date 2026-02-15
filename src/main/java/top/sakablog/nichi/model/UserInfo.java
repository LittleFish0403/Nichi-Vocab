package top.sakablog.nichi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

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
    @Column(name="user_id", insertable=false, updatable=false)
    private Long user_id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserWordBookRelation selectedWordBook;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserWordBookRelation> userWordBookRelations;
}
