package top.sakablog.nichi.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sakablog.nichi.common.exception.BusinessException;
import top.sakablog.nichi.common.exception.SystemException;
import top.sakablog.nichi.model.entity.Book;
import top.sakablog.nichi.model.entity.user.User;
import top.sakablog.nichi.model.entity.study.UserBookProgress;
import top.sakablog.nichi.model.entity.user.UserProfile;
import top.sakablog.nichi.repository.BookRepository;
import top.sakablog.nichi.repository.UserBookProgressRepository;
import top.sakablog.nichi.service.UserProfileService;
import top.sakablog.nichi.service.UserService;
import top.sakablog.nichi.service.UserBookProgressService;

import java.util.List;

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
public class UserBookProgressServiceImpl implements UserBookProgressService {
    @Autowired
    UserBookProgressRepository userBookProgressRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    BookServiceImpl wordBookService;


    /**
     * 用户选择单词本
     */
    @Override
    @Transactional
    public UserBookProgress createUserBookProgress(Long userId, Long bookId){
        if (!userService.existsByUserId(userId) || !wordBookService.existsByBookId(bookId)) {
            throw new BusinessException("请输入有效的用户ID和单词本ID");
        }
        try {
            User user = userService.findUserByUserId(userId);
            Book book = wordBookService.getWordBookById(bookId);

            if (userBookProgressRepository.existsUserWordBookRelationByUserIdAndBookId(userId, bookId)) {
                if (user.getUserProfile().getSelectedBook() != null && user.getUserProfile().getSelectedBook().getId().equals(bookId)) {
                    throw new BusinessException("重复选择词书");
                } else {
                    UserBookProgress previousProgress = userBookProgressRepository.findById(user.getUserProfile().getSelectedUserBookProgressId())
                            .orElseThrow(() -> new BusinessException("之前选择的词书进度不存在，ID: " + user.getUserProfile().getSelectedUserBookProgressId()));
                    previousProgress.setIsActive(false);
                    userBookProgressRepository.save(previousProgress);

                    UserBookProgress currentProgress = userBookProgressRepository.findByUserIdAndBookId(userId, bookId);
                    currentProgress.setIsActive(true);
                    UserProfile userProfile = user.getUserProfile();
                    userProfile.setSelectedBook(book);
                    userProfile.setSelectedUserBookProgress(currentProgress);
                    return userBookProgressRepository.save(currentProgress);
                }
            } else {
                UserBookProgress userBookProgress = userBookProgressRepository.save(new UserBookProgress()
                        .setUserProfile(user.getUserProfile())
                        .setBook(book)
                        .setUserId(userId)
                        .setBookId(bookId))
                        .setIsActive(true);
                if (user.getUserProfile().getSelectedUserBookProgressId() != null) {
                    UserBookProgress previousProgress = userBookProgressRepository.findById(user.getUserProfile().getSelectedUserBookProgressId())
                            .orElseThrow(() -> new BusinessException("之前选择的词书进度不存在，ID: " + user.getUserProfile().getSelectedUserBookProgressId()));
                    previousProgress.setIsActive(false);
                    userBookProgressRepository.save(previousProgress);
                }
                user.getUserProfile().setSelectedBook(book);
                user.getUserProfile().setSelectedUserBookProgress(userBookProgress);
                return userBookProgressRepository.save(userBookProgress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : e.toString();
            throw new SystemException("选择单词本出现问题: " + errorMsg);
        }
    }

    /**
     * 用户取消选择单词本
     */
    public void deleteUserBookProgress(Long userId, Long bookId){
        try {
            userBookProgressRepository.deleteByUserIdAndBookId(userId, bookId);
        } catch (Exception e) {
            throw new SystemException("取消选择单词本出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询用户选择的单词本关系
     */
    public List<Book> getSelectedBooksByUserId(Long userId){
        try {
            return bookRepository.findByUserBookProgressId(userId);
        } catch (Exception e) {
            throw new SystemException("根据用户ID查询用户选择的单词本关系出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据用户ID和单词本ID查找对应的用户单词本关系
     */
    public UserBookProgress getUserBookProgressByUserIdAndBookId(Long userId, Long bookId){
        try {
            return userBookProgressRepository.findByUserIdAndBookId(userId, bookId);
        } catch (Exception e) {
            throw new SystemException("根据用户ID和单词本ID查找对应的用户单词本关系出现问题: " + e.getMessage());
        }
    }

    /**
     * 根据ID查找对应的用户单词本关系
     */
    public UserBookProgress findUserBookProgressById(Long id) {
        try {
            return userBookProgressRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("用户单词本关系不存在，ID: " + id));
        } catch (Exception e) {
            throw new SystemException("根据ID查找对应的用户单词本关系出现问题: " + e.getMessage());
        }
    }
}
