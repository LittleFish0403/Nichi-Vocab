### 1. word (单词主表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **entry_id** | VARCHAR(100) | 词条全局唯一 ID | entry_id |
| **entry_type** | VARCHAR(20) | 词条类型 (word/grammar) | entry_type |
| **headword** | VARCHAR(100) | 主词头 | headword |
| **headword_kana** | VARCHAR(100) | 主读音 | headword_kana |
| **kana_only** | TINYINT(1) | 是否假名词 | kana_only |
| **created_at** | DATETIME | 创建时间 | - |
| **updated_at** | DATETIME | 更新时间 | - |

### 2. word_sense (单词义项表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **word_id** | Long (FK) | 关联的主表 word.id | - |
| **sense_id** | VARCHAR(100) | 义项唯一 ID | sense_id |
| **source** | VARCHAR(50) | 该义项来源 | source |
| **source_ref** | VARCHAR(50) | 来源内部定位信息 | source_ref |
| **pos** | VARCHAR(50) | 标准化词性 (如 v5k-s) | pos |
| **pos_raw** | VARCHAR(50) | 原始词性 (如 动1) | pos_raw |
| **gloss_zh_hans** | VARCHAR(500) | 中文释义，数组转字符串 | gloss_zh_hans |
| **gloss_en** | VARCHAR(500) | 英文释义，数组转字符串 | gloss_en |

### 3. word_source_occurrence (单词来源出现记录表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **sense_id** | Long (FK) | 关联的义项表 word_sense.id | - |
| **row_index** | INT | 原始教材行号 | row_index |
| **lesson_num** | INT | 课次编号 | lesson_num |
| **lesson_label** | VARCHAR(100) | 课次标签 | lesson_label |
| **textbook_set** | VARCHAR(100) | 教材集合 | textbook_set |

### 4. word_classification (单词分类表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **word_id** | Long (FK) | 关联的主表 word.id | - |
| **jlpt_levels** | VARCHAR(50) | JLPT等级，逗号拼接 | classifications.jlpt_levels |
| **textbook_sets** | VARCHAR(255) | 教材系列，逗号拼接 | classifications.textbook_sets |
| **textbook_lessons** | VARCHAR(500) | 教材课次，逗号拼接 | classifications.textbook_lessons |
| **frequency_tags** | VARCHAR(100) | 词频标签，逗号拼接 | classifications.frequency_tags |
| **is_common** | TINYINT(1) | 是否为常用词 | study_meta.is_common |
| **is_grammar** | TINYINT(1) | 是否为语法点 | study_meta.is_grammar |
| **is_expression** | TINYINT(1) | 是否为表达句/短语 | study_meta.is_expression |
| **is_name** | TINYINT(1) | 是否为专有名词 | study_meta.is_name |

### 5. word_pronunce (单词发音表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **word_id** | Long (FK) | 关联的主表 word.id | - |
| **type** | VARCHAR(50) | 发音类型 (当前为 accent) | type |
| **reading** | VARCHAR(100) | 对应读音 | reading |
| **source_name** | VARCHAR(50) | 来源名称 | source_name |
| **source_ref** | VARCHAR(50) | 来源定位 | source_ref |
| **accent_type** | VARCHAR(50) | 声调类型描述 | accent.accent_type |
| **pattern** | VARCHAR(255) | 拍的拆分数组，逗号拼接 | accent.pattern |
| **accent_number** | INT | 声调数字 (如 0) | accent.accent_number |
| **drop_after_mora** | INT | 核下降点 (第几拍后下降) | accent.drop_after_mora |
| **mora_count** | INT | 总拍数 | accent.mora_count |

### 6. word_example (单词例句表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **word_id** | Long (FK) | 关联的主表 word.id | - |
| **ja** | TEXT | 日文例句 | ja |
| **zh_hans** | TEXT | 中文翻译 | zh_hans |
| **lesson_num** | INT | 课次编号 | lesson_num |
| **lesson_label** | VARCHAR(100) | 课次标签 | lesson_label |
| **textbook_set** | VARCHAR(100) | 教材集合 | textbook_set |
| **section** | VARCHAR(50) | 所在小节 (如 会话) | section |
| **source** | VARCHAR(50) | 例句来源 | source |

### 7. word_relation (单词关联表)

| **字段名** | **数据类型** | **业务说明** | **对应 V2 字段** |
| --- | --- | --- | --- |
| **id** | Long (PK) | 内部主键 | - |
| **word_id** | Long (FK) | 当前词的 word.id (起点) | - |
| **type** | VARCHAR(50) | 关系类型 (如 lemma) | type |
| **target_entry_id** | VARCHAR(100) | 目标词条 ID (终点) | target_entry_id |
| **target_headword** | VARCHAR(100) | 目标词头 | target_headword |
| **target_reading** | VARCHAR(100) | 目标读音 | target_reading |
| **source** | VARCHAR(50) | 关系生成来源 | source |
| **confidence** | VARCHAR(20) | 置信度 (high/medium) | confidence |
| **parsed_surface** | VARCHAR(100) | 识别词形 | parsed_surface |
| **parsed_pos** | VARCHAR(50) | 识别词类 | parsed_pos |