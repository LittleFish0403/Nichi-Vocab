package top.sakablog.nichi.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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

@Component
public class CsvUtils {
    /**
     * 使用 OpenCSV 的 CsvToBeanBuilder 将 CSV 内容解析为指定类型的对象列表
     *
     * @param reader [参数说明]
     * @param clazz  [参数说明]
     * @return [List<T>]
     */
    public <T> List<T> beanBuilder(Reader reader, Class<T> clazz) {
        return new CsvToBeanBuilder<T>(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .withOrderedResults(true)
                .build()
                .parse();
    }

    // 你的便捷方法：接受 Path，内部调用上面的方法
    public <T> List<T> beanBuilder(Path path, Class<T> clazz) throws Exception {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return beanBuilder(reader, clazz);
        }
    }
}
