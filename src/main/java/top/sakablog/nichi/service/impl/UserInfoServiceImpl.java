package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.UserInfoMapper;
import top.sakablog.nichi.model.dto.UserInfoDto;
import top.sakablog.nichi.repository.UserInfoRepository;
import top.sakablog.nichi.service.UserInfoService;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据用户ID获取用户信息
     */
    public UserInfoDto getUserInfoByUserId(Long userId){
        try {
            if (!userInfoRepository.existsByUserId(userId)) {
                throw new BusinessException("用户信息不存在");
            }
            return userInfoMapper.toDto(userInfoRepository.findByUserId(userId));
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * 根据用户ID获取用户选择的词书
     */
    public UserInfoDto getUserWordBookByUserId(Long userId){
        return null;
    }
}
