package top.sakablog.nichi.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderNameBaseMappingStrategy;
import org.springframework.stereotype.Component;
import top.sakablog.nichi.model.dto.ImportWordDto;

import java.io.IOException;
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
     * 【通用方法】将 CSV 文件内容解析并映射到指定的 Bean 类型列表。
     * * @param <T>   要映射的 Bean 类型（必须是带有 OpenCSV 注解的类，如 WordImportDTO）
     * @param path  CSV 文件的路径
     * @param clazz 要映射的 Bean 类的 Class 对象 (如 WordImportDTO.class)
     * @return 映射后的 Bean 对象列表
     * @throws IOException      文件读写错误
     * @throws IllegalStateException CSV 解析过程中遇到非法状态
     */
    public <T> List<T> beanBuilder(Path path, Class<T> clazz) throws Exception {
        // 1. 显式指定编码（防止中文乱码导致表头匹配失败）
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withOrderedResults(true)
                    // 关键点：如果不凑效，OpenCSV 默认会尝试使用 HeaderColumnNameMappingStrategy
                    .build();

            return csvToBean.parse();
        }
    }
}
