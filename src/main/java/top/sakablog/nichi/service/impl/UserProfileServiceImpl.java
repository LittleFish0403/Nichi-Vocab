package top.sakablog.nichi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.mapper.UserProfileMapper;
import top.sakablog.nichi.model.dto.UserProfileDto;
import top.sakablog.nichi.repository.UserProfileRepository;
import top.sakablog.nichi.service.UserProfileService;

/**
 * UserProfileServiceImpl
 * <p>
 * UserProfileService的实现类，提供用户信息相关的具体业务逻辑实现
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    /**
     * 根据用户ID获取用户信息
     */
    public UserProfileDto getUserProfileByUserId(Long userId){
        try {
            if (!userProfileRepository.existsByUserId(userId)) {
                throw new BusinessException("用户信息不存在");
            }
            return userProfileMapper.toDto(userProfileRepository.findByUserId(userId));
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * 获取用户每组单词数量
     */
    public Integer getNumberOfWordsPerSession(Long userId){
        try{
            return userProfileRepository.findByUserId(userId)
                    .getNumberOfWordsPerSession();
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("用户信息不存在");
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }

    /**
     * 获取用户单词回传大小
     */
    public Integer getFeedBackSize(Long userId){
        try{
            return userProfileRepository.findByUserId(userId)
                    .getFeedbackSize();
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("用户信息不存在");
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }
    }
}
