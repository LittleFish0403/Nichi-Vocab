package top.sakablog.nichi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sakablog.nichi.model.dto.word.importer.WordV2AccentImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2EntryImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2ExampleImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2PronunciationImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2RelationImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2SenseImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2SourceOccurrenceImportDto;
import top.sakablog.nichi.model.dto.word.importer.WordV2StudyMetaImportDto;
import top.sakablog.nichi.model.entity.word.*;
import top.sakablog.nichi.repository.word.*;

import java.io.File;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author <a href="mailto:1041365078@qq.com">Sakana</a>
 * @version 1.0.1
 * @since 1.0.0
 */
@SpringBootTest
public class JacksonDemoTest {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/main/resources/static/dict_v3/compact_phase3_entries.json");
    File testFile = new File("src/test/resources/test.json");

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordSenseRepository wordSenseRepository;

    @Autowired
    private WordClassificationRepository wordClassificationRepository;

    @Autowired
    private WordPronunciationRepository wordPronunciationRepository;

    @Autowired
    private WordRelationRepository wordRelationRepository;

    @Autowired
    private WordExampleRepository wordExampleRepository;

    @Autowired
    private WordSourceOccurrenceRepository wordSourceOccurrenceRepository;

    @Test
    public void mapTest() throws Exception {
        List<WordV2EntryImportDto> dto = mapper.readValue(file, new TypeReference<List<WordV2EntryImportDto>>(){});
        System.out.println(dto);
    }

    @Test
    public void importTest() throws Exception {
        List<WordV2EntryImportDto> dtos = mapper.readValue(file, new TypeReference<List<WordV2EntryImportDto>>(){});
        Set<String> processedEntryIds = new HashSet<>();

        for (WordV2EntryImportDto dto : dtos) {
            if (!processedEntryIds.add(dto.getEntryId())) {
                System.err.println("跳过重复的 entry_id: " + dto.getEntryId());
                continue;
            }

            //word
            Word word = new Word()
                    .setEntryId(dto.getEntryId())
                    .setEntryType(dto.getEntryType())
                    .setHeadword(dto.getHeadword())
                    .setHeadwordKana(dto.getHeadwordKana())
                    .setKanaOnly(dto.getKanaOnly());
            word = wordRepository.save(word);

            //word_sense
            List<WordSense> wordSenses = new ArrayList<>();
            for (WordV2SenseImportDto sense : safeList(dto.getSenses())) {
                WordSense wordSense = new WordSense()
                        .setSenseId(sense.getSenseId())
                        .setSource(sense.getSource())
                        .setSourceRef(sense.getSourceRef())
                        .setPos(sense.getPos())
                        .setPosRaw(sense.getPosRaw())
                        .setGlossZhHans(joinList(sense.getGlossZhHans()))
                        .setGlossEn(joinList(sense.getGlossEn()))
                        .setWord(word);
                wordSenseRepository.save(wordSense);
                for (WordV2SourceOccurrenceImportDto occurrence : safeList(dto.getSourceOccurrences())) {
                    if (Objects.equals(occurrence.getSource(), sense.getSource()) && Objects.equals(occurrence.getLessonNum()+":"+occurrence.getRowIndex(), sense.getSourceRef())) {
                        WordSourceOccurrence senseSourceOccurrence = new WordSourceOccurrence()
                                .setSense(wordSense)
                                .setRowIndex(occurrence.getRowIndex())
                                .setLessonNum(occurrence.getLessonNum())
                                .setLessonLabel(occurrence.getLessonLabel())
                                .setTextbookSet(occurrence.getTextbookSet());
                        WordSourceOccurrence saved = wordSourceOccurrenceRepository.save(senseSourceOccurrence);
                        wordSense.setSourceOccurrence(saved);
                    }
                }
                wordSenses.add(wordSense);
            }
            if (!wordSenses.isEmpty()) {
                wordSenses = wordSenseRepository.saveAll(wordSenses);
            }

            //word_classification
            WordV2StudyMetaImportDto studyMeta = dto.getStudyMeta();
            WordClassification wordClassification = new WordClassification()
                    .setWord(word);
            if (dto.getClassifications() != null) {
                wordClassification
                        .setJlptLevels(joinList(dto.getClassifications().getJlptLevels()))
                        .setTextbookSets(joinList(dto.getClassifications().getTextbookSets()))
                        .setTextbookLessons(joinList(dto.getClassifications().getTextbookLessons()))
                        .setFrequencyTags(joinList(dto.getClassifications().getFrequencyTags()));
            }
            if (studyMeta != null) {
                wordClassification
                        .setIsCommon(studyMeta.getIsCommon())
                        .setIsGrammar(studyMeta.getIsGrammar())
                        .setIsExpression(studyMeta.getIsExpression())
                        .setIsName(studyMeta.getIsName());
            }
            if (dto.getClassifications() != null || studyMeta != null) {
                wordClassificationRepository.save(wordClassification);
            }

            //word_pronounce
            List<WordPronunciation> wordPronounces = new ArrayList<>();
            for (WordV2PronunciationImportDto pronunciation : safeList(dto.getPronunciations())) {
                WordPronunciation wordPronunciation = new WordPronunciation()
                        .setWord(word)
                        .setType(pronunciation.getType())
                        .setReading(pronunciation.getReading())
                        .setSourceName(pronunciation.getSourceName())
                        .setSourceRef(pronunciation.getSourceRef());

                WordV2AccentImportDto accent = pronunciation.getAccent();
                if (accent != null) {
                    wordPronunciation
                            .setAccentType(accent.getAccentType())
                            .setPattern(joinList(accent.getPattern()))
                            .setAccentNumber(accent.getAccentNumber())
                            .setDropAfterMora(accent.getDropAfterMora())
                            .setMoraCount(accent.getMoraCount());
                }
                wordPronounces.add(wordPronunciation);
            }
            if (!wordPronounces.isEmpty()) {
                wordPronunciationRepository.saveAll(wordPronounces);
            }

            //word_relation
            List<WordRelation> wordRelations = new ArrayList<>();
            for (WordV2RelationImportDto relation : safeList(dto.getRelations())) {
                WordRelation wordRelation = new WordRelation()
                        .setWord(word)
                        .setType(relation.getType())
                        .setTargetEntryId(relation.getTargetEntryId())
                        .setTargetHeadword(relation.getTargetHeadword())
                        .setTargetReading(relation.getTargetReading())
                        .setSource(relation.getSource())
                        .setConfidence(relation.getConfidence())
                        .setParsedSurface(relation.getParsedSurface())
                        .setParsedPos(relation.getParsedPos());
                wordRelations.add(wordRelation);
            }
            if (!wordRelations.isEmpty()) {
                wordRelationRepository.saveAll(wordRelations);
            }

            //word_example
            List<WordExample> wordExamples = new ArrayList<>();
            for (WordV2ExampleImportDto example : safeList(dto.getExamples())) {
                WordExample wordExample = new WordExample()
                        .setWord(word)
                        .setJa(example.getJa())
                        .setZhHans(example.getZhHans())
                        .setLessonNum(example.getLessonNum())
                        .setLessonLabel(example.getLessonLabel())
                        .setTextbookSet(example.getTextbookSet())
                        .setSection(example.getSection())
                        .setSource(example.getSource());
                wordExamples.add(wordExample);
            }
            if (!wordExamples.isEmpty()) {
                wordExampleRepository.saveAll(wordExamples);
            }
        }
    }

    @Test
    public void deleteDB() {
        wordSourceOccurrenceRepository.deleteAll();
        wordExampleRepository.deleteAll();
        wordRelationRepository.deleteAll();
        wordPronunciationRepository.deleteAll();
        wordClassificationRepository.deleteAll();
        wordSenseRepository.deleteAll();
        wordRepository.deleteAll();
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    private String joinList(List<String> values) {
        return values == null || values.isEmpty() ? null : String.join(", ", values);
    }
}
