package net.langreader.controller;

import net.langreader.dao.TextRepository;
import net.langreader.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/texts")
public class TextRestController {
    private final TextRepository textRepository;

    @Autowired
    public TextRestController(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @GetMapping
    public ResponseEntity<List<Text>> getAllTextTitles() {
        List<Text> texts = textRepository.findAll();
        for (Text text : texts) {
            text.setText(null);
        }
        texts.sort(Comparator.comparing(Text::getId).reversed());
        return new ResponseEntity<>(texts, HttpStatus.OK);
    }

    @GetMapping("/{textId}")
    public ResponseEntity<Text> getTextWithId(@PathVariable int textId) {
        Optional<Text> foundText = textRepository.findById(textId);
        return foundText.map(text -> new ResponseEntity<>(text, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Text> addText(@RequestBody Text newText) {
        String textVal = newText.getText();
        if (textVal == null || textVal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            newText.setId(null);
            textRepository.save(newText);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<Text> updateText(@RequestBody Text text) {
        if (text.getText() == null || text.getText().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!textRepository.existsById(text.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            textRepository.save(text);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Text> deleteText(@RequestBody Text text) {
        if (!textRepository.existsById(text.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            textRepository.deleteById(text.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
