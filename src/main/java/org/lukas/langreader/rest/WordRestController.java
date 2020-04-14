package org.lukas.langreader.rest;

import org.lukas.langreader.dao.WordRepository;
import org.lukas.langreader.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: in the future, add GET APIs for statistics maybe
 */
@RestController
@RequestMapping("/words")
public class WordRestController {
    private final WordRepository wordRepository;

    private final String INVALID_WORD_MSG = "Provided word '%s' is not valid.";
    private final String MISSING_TYPE_MSG = "Type of word missing. " +
            "Choose type from [KNOWN, STUDIED, IGNORED].";
    private final String NOT_IN_DB_MSG = "Provided word '%s' isn't in the DB yet.";

    @Autowired
    public WordRestController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping
    public String hello() {
        return "Hello world";
    }

    @PostMapping("/enrich")
    public List<Word> enrichWords(
            @RequestBody List<Word> typelessWords) {

        List<Word> enrichedWords = new ArrayList<>();
        for (Word typelessWord : typelessWords) {
            Word foundWord = wordRepository.findByWord(
                    typelessWord.getWord());
            if (foundWord != null) {
                /*
                    To prevent case-sensitivity of tokens - use the typeless obj reference
                    with type added. We are returning the original values for keeping the
                    specific letter cases from the read document.
                */
                typelessWord.setType(foundWord.getType());
                typelessWord.setId(foundWord.getId());
            }
            enrichedWords.add(typelessWord);
        }
        return enrichedWords;
    }

    @PostMapping
    public ResponseEntity<WordResponse> addWord(
            @RequestBody Word newWord) {

        WordResponse response = validateAddWordRequest(newWord);

        if (response.getStatus() == 200) {
            newWord.setId(null);
            // to ensure case-insensitivity of tokens
            newWord.setWord(newWord.getWord().toLowerCase());
            wordRepository.save(newWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping
    public ResponseEntity<WordResponse> updateWord(
            @RequestBody Word word) {

        WordResponse response = validateUpdateWordRequest(word);

        if (response.getStatus() == 200) {
            Word foundWord = wordRepository.findByWord(
                    word.getWord().toLowerCase());
            response.setMessage(response.getMessage());
            // already checked that it's in the DB
            // also, saving the found obj to preserve case-insensivity
            foundWord.setType(word.getType());
            wordRepository.save(foundWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @DeleteMapping
    public ResponseEntity<WordResponse> deleteWord(
            @RequestBody Word word) {

        WordResponse response = validateDeleteWordRequest(word);

        if (response.getStatus() == 200) {
            Word foundWord = wordRepository.findByWord(
                    word.getWord().toLowerCase());
            // already checked that it's in the DB
            wordRepository.delete(foundWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    private WordResponse validateAddWordRequest(Word word) {
        WordResponse response = new WordResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (word.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (wordRepository.existsByWord(wordVal.toLowerCase())) {
            status = HttpStatus.CONFLICT;
            message = "Duplicate word. Word '" + wordVal + "' " +
                    "or its variations already exist.";
        } else {
            status = HttpStatus.OK;
            message = "Word saved.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }

    private WordResponse validateUpdateWordRequest(Word word) {
        WordResponse response = new WordResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (word.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (!wordRepository.existsByWord(wordVal.toLowerCase())) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(NOT_IN_DB_MSG, wordVal);
        } else {
            status = HttpStatus.OK;
            message = "Word updated.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }

    private WordResponse validateDeleteWordRequest(Word word) {
        WordResponse response = new WordResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (!wordRepository.existsByWord(wordVal.toLowerCase())) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(NOT_IN_DB_MSG, wordVal);
        } else {
            status = HttpStatus.OK;
            message = "Word deleted.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return response;
    }
}
