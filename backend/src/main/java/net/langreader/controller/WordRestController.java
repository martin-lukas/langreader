package net.langreader.controller;

import net.langreader.dao.WordRepository;
import net.langreader.model.Word;
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

    @Autowired
    public WordRestController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @PostMapping("/enrich")
    public ResponseEntity<List<Word>> enrichWords(@RequestBody List<Word> words) {
        List<Word> enrichedWords = new ArrayList<>();
        for (Word typelessWord : words) {
            Word foundWord = wordRepository.findByWord(typelessWord.getWord().toLowerCase());
            if (foundWord != null) {
                typelessWord.setType(foundWord.getType());
            }
            enrichedWords.add(typelessWord);
        }
        return new ResponseEntity<>(enrichedWords, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Word> addWord(@RequestBody Word newWord) {
        String newWordVal = newWord.getWord();
        if (newWordVal == null || newWordVal.isEmpty() || newWord.getType() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (wordRepository.existsByWord(newWordVal)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            newWord.setId(null);
            // to ensure case-insensitivity of tokens
            newWord.setWord(newWord.getWord());
            wordRepository.save(newWord);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Word> updateWord(@RequestBody Word word) {
        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty() || word.getType() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!wordRepository.existsByWord(wordVal)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Word foundWord = wordRepository.findByWord(word.getWord());
            // already checked that it's in the DB
            // also, saving the found obj to preserve case-insensivity
            foundWord.setType(word.getType());
            wordRepository.save(foundWord);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Word> deleteWord(@RequestBody Word word) {
        String wordVal = word.getWord();
        if (wordVal == null || wordVal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!wordRepository.existsByWord(wordVal)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Word foundWord = wordRepository.findByWord(word.getWord());
            // already checked that it's in the DB
            wordRepository.delete(foundWord);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
