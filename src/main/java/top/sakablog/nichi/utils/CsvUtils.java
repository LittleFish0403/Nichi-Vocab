package top.sakablog.nichi.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import top.sakablog.nichi.model.dto.ImportWordDto;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * CsvUtils
 * <p>
 * CSV 文件操作工具类
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
public class CsvUtils {

    /**
     * 【通用方法】将 CSV 文件内容解析并映射到指定的 Bean 类型列表。
     * * @param <T>   要映射的 Bean 类型（必须是带有 OpenCSV 注解的类，如 WordImportDTO）
     * @param path  CSV 文件的路径
     * @param clazz 要映射的 Bean 类的 Class 对象 (如 WordImportDTO.class)
     * @return 映射后的 Bean 对象列表
     * @throws IOException      文件读写错误
     * @throws IllegalStateException CSV 解析过程中遇到非法状态
     */
    public static <T> List<T> beanBuilder(Path path, Class<T> clazz) throws IOException, IllegalStateException {

        // 使用 Files.newBufferedReader(path) 确保文件以适当的字符集打开
        try (Reader reader = Files.newBufferedReader(path)) {

            // 1. **修正泛型**：CsvToBeanBuilder 的泛型和 build() 后的对象类型应为 <T>
            CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true) // 忽略空格，增加健壮性
                    .withSkipLines(1)                  // **新增：跳过 CSV 文件中的第一行（通常是标题行）**
                    .build();

            // 2. 移除 throws Exception，使用更具体的异常类型
            return cb.parse();
        }
    }
}
