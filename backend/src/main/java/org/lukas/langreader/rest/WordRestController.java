package org.lukas.langreader.rest;

import org.lukas.langreader.dao.WordRepository;
import org.lukas.langreader.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/words")
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

    @PostMapping("/enrich")
    public List<Word> enrichWords(@RequestBody List<Word> words) {
        List<Word> enrichedWords = new ArrayList<>();
        for (Word typelessWord : words) {
            Word foundWord = wordRepository.findByWord(typelessWord.getWord().toLowerCase());
            if (foundWord != null) {
                /*
                    To prevent case-sensitivity of tokens - use the typeless obj reference
                    with type added. We are returning the original values for preserving
                    case sensitivity in the read document.
                */
                typelessWord.setType(foundWord.getType());
            }
            enrichedWords.add(typelessWord);
        }
        return enrichedWords;
    }

    @PostMapping
    public ResponseEntity<RestResponse> addWord(@RequestBody Word newWord) {
        RestResponse response = validateAddWordRequest(newWord);

        if (response.getStatus() == 200) {
            newWord.setId(null);
            // to ensure case-insensitivity of tokens
            newWord.setWord(newWord.getWord());
            wordRepository.save(newWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping
    public ResponseEntity<RestResponse> updateWord(@RequestBody Word word) {
        RestResponse response = validateUpdateWordRequest(word);

        if (response.getStatus() == 200) {
            Word foundWord = wordRepository.findByWord(word.getWord());
            // already checked that it's in the DB
            // also, saving the found obj to preserve case-insensivity
            foundWord.setType(word.getType());
            wordRepository.save(foundWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @DeleteMapping
    public ResponseEntity<RestResponse> deleteWord(@RequestBody Word word) {
        RestResponse response = validateDeleteWordRequest(word);

        if (response.getStatus() == 200) {
            Word foundWord = wordRepository.findByWord(
                    word.getWord().toLowerCase());
            // already checked that it's in the DB
            wordRepository.delete(foundWord);
        }

        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    private RestResponse validateAddWordRequest(Word word) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (word.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (wordRepository.existsByWord(wordVal)) {
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

    private RestResponse validateUpdateWordRequest(Word word) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (word.getType() == null) {
            status = HttpStatus.BAD_REQUEST;
            message = MISSING_TYPE_MSG;
        } else if (!wordRepository.existsByWord(wordVal)) {
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

    private RestResponse validateDeleteWordRequest(Word word) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(INVALID_WORD_MSG, wordVal);
        } else if (!wordRepository.existsByWord(wordVal)) {
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
