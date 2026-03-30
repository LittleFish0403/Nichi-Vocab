# V2 JSON Schema

本文档说明当前 v2 链路使用的标准 JSON 结构。  
约定：

- `必填`：字段应始终存在
- `可选`：字段仅在有值或阶段已生成时出现

## 1. Entry

```json
{
  "entry_id": "jp:行く:いく",
  "entry_type": "word",
  "headword": "行く",
  "headword_kana": "いく",
  "kana_only": false,
  "orthographies": [],
  "senses": [],
  "source_tags": [],
  "classifications": {},
  "source_occurrences": [],
  "study_meta": {},
  "relations": [],
  "pronunciations": [],
  "examples": []
}
```

### Entry 字段

- `entry_id`：`string`，必填。全库唯一 ID。
- `entry_type`：`string`，必填。当前主要是 `word`，也可扩展为 `grammar`。
- `headword`：`string`，必填。词头。
- `headword_kana`：`string | null`，可选。读音。
- `kana_only`：`boolean`，必填。是否假名词。
- `orthographies`：`array`，必填。词形列表。
- `senses`：`array`，必填。义项列表。
- `source_tags`：`array[string]`，必填。来源标签。
- `classifications`：`object`，可选。分类信息。
- `source_occurrences`：`array`，可选。教材原始出现记录。
- `study_meta`：`object`，可选。学习侧辅助标记。
- `relations`：`array`，可选。词形或词根关系。
- `pronunciations`：`array`，可选。重音等读音信息。
- `examples`：`array`，可选。例句。

## 2. Orthography

```json
{
  "text": "行く",
  "kind": "kanji",
  "common": true,
  "sources": ["jmdict"]
}
```

- `text`：`string`，必填。词形文本。
- `kind`：`string`，必填。通常为 `kanji` 或 `kana`。
- `common`：`boolean`，可选。常用标记。
- `sources`：`array[string]`，可选。该词形来源。

## 3. Sense

```json
{
  "sense_id": "jp:行く:いく:jmdict:1:1",
  "source": "jmdict",
  "source_ref": "1578850:1",
  "pos": "v5k-s",
  "gloss_zh_hans": ["去"],
  "gloss_en": ["to go"],
  "pos_raw": "动1",
  "notes": ["after the -te form of a verb"]
}
```

- `sense_id`：`string`，必填。义项唯一 ID。
- `source`：`string`，必填。该义项来源。
- `source_ref`：`string`，必填。来源内部定位信息。
- `pos`：`string`，可选。标准化词性。
- `gloss_zh_hans`：`array[string]`，可选。中文释义。
- `gloss_en`：`array[string]`，可选。英文释义。
- `pos_raw`：`string`，可选。原始词性，主要用于教材词。
- `notes`：`array[string]`，可选。备注。

约束：

- 一个 `sense` 只对应一个词性组。
- 新标日每一行单独对应一个 `sense`，不与其他行合并。

## 4. Classifications

```json
{
  "jlpt_levels": ["N5"],
  "textbook_sets": ["xinbiao_chuji_1"],
  "textbook_lessons": ["xinbiao_chuji_1_lesson_06"],
  "frequency_tags": ["common"]
}
```

字段均为可选：

- `jlpt_levels`：`array[string]`
- `textbook_sets`：`array[string]`
- `textbook_lessons`：`array[string]`
- `frequency_tags`：`array[string]`

## 5. Source Occurrence

```json
{
  "source": "biaori-words",
  "row_index": 280,
  "lesson_num": 105,
  "lesson_label": "xinbiao_chuji_1_lesson_06",
  "textbook_set": "xinbiao_chuji_1",
  "word_surface": "行きます",
  "reading": "いきます",
  "pos_raw": "动1",
  "gloss_zh_hans": "去",
  "audio_range": [39.957, 40.899]
}
```

- `source`：`string`，必填。
- `row_index`：`integer`，必填。原始教材行号。
- `lesson_num`：`integer`，必填。
- `lesson_label`：`string`，必填。
- `textbook_set`：`string`，必填。
- `word_surface`：`string`，必填。
- `reading`：`string | null`，可选。
- `pos_raw`：`string`，可选。
- `gloss_zh_hans`：`string`，可选。
- `audio_range`：`array[number, number]`，可选。

## 6. Study Meta

```json
{
  "is_common": true,
  "is_grammar": false,
  "is_expression": false,
  "is_name": false
}
```

字段均为可选：

- `is_common`：`boolean`
- `is_grammar`：`boolean`
- `is_expression`：`boolean`
- `is_name`：`boolean`

## 7. Relation

```json
{
  "type": "lemma",
  "target_entry_id": "jp:行く:いく",
  "target_headword": "行く",
  "target_reading": "いく",
  "source": "mecab-unidic-lite",
  "confidence": "high",
  "method": "exact_head_reading",
  "parsed_surface": "行き",
  "parsed_pos": "動詞"
}
```

- `type`：`string`，必填。当前使用 `lemma`。
- `target_entry_id`：`string`，必填。目标词条 ID。
- `target_headword`：`string`，必填。目标词头。
- `target_reading`：`string`，可选。目标读音。
- `source`：`string`，必填。关系生成来源。
- `confidence`：`string`，必填。当前为 `high` / `medium`。
- `method`：`string`，可选。匹配方式。
- `parsed_surface`：`string`，可选。MeCab 实际识别到的词形。
- `parsed_pos`：`string`，可选。MeCab 词类。

## 8. Pronunciation

```json
{
  "type": "accent",
  "reading": "いく",
  "source_name": "n5n4",
  "source_ref": "123",
  "accent": {
    "system": "tokyo",
    "accent_number": 0,
    "accent_type": "heiban",
    "pattern": ["い", "く"],
    "drop_after_mora": 0,
    "mora_count": 2,
    "source": "ojad",
    "confidence": "medium"
  }
}
```

- `type`：`string`，必填。当前为 `accent`。
- `reading`：`string`，必填。
- `source_name`：`string`，必填。
- `source_ref`：`string`，必填。
- `accent`：`object`，必填。

### Accent

- `system`：`string`，必填。
- `accent_number`：`integer`，必填。
- `accent_type`：`string`，必填。
- `pattern`：`array[string]`，必填。
- `drop_after_mora`：`integer`，必填。
- `mora_count`：`integer`，必填。
- `source`：`string`，必填。
- `confidence`：`string`，必填。

## 9. Example

```json
{
  "ja": "李さんは 毎朝 パンを 食べます。",
  "zh_hans": "小李每天早晨吃面包。",
  "lesson_num": 105,
  "lesson_label": "xinbiao_chuji_1_lesson_06",
  "textbook_set": "xinbiao_chuji_1",
  "section": "会话",
  "source": "biaori-lessons",
  "audio": {
    "lesson_num": 105,
    "audio_type": 0,
    "start": 39.957,
    "end": 40.899
  }
}
```

- `ja`：`string`，必填。
- `zh_hans`：`string`，必填。
- `lesson_num`：`integer`，必填。
- `lesson_label`：`string`，必填。
- `textbook_set`：`string`，必填。
- `section`：`string`，可选。
- `source`：`string`，必填。
- `audio`：`object`，可选。

## 10. 阶段性差异

### Phase1 v2

- 一定有：`entry_id`、`headword`、`orthographies`、`senses`
- 可能没有：`relations`、`pronunciations`、`examples`

### Phase2 v2

- 在 phase1 基础上增加了 `relations`
- 仍然可能没有：`pronunciations`、`examples`

### Phase3 v2

- 在 phase2 基础上增加了 `pronunciations` 和 `examples`
- 但两者都仍是可选字段，不保证每个词条都有

## 11. 设计原则

- 词条主键以“词面 + 读音”绑定
- `sense` 不混词性，不混来源行
- 不确定的信息宁可缺失，不强行补齐
- 所有跨词条关系尽量落到 `target_entry_id`
