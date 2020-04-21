package org.lukas.langreader.rest;

import org.lukas.langreader.dao.TextRepository;
import org.lukas.langreader.entity.Text;
import org.lukas.langreader.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/texts")
public class TextRestController {
    private final TextRepository textRepository;

    private final String INVALID_TEXT_MSG = "Provided text is not valid.";
    private final String NOT_IN_DB_MSG = "Text with the ID '%d' isn't in the DB.";

    @Autowired
    public TextRestController(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @GetMapping
    public List<Text> getAllTextTitles() {
        List<Text> texts = textRepository.findAll();
        for (Text text : texts) {
            text.setText(null);
        }
        return texts;
    }

    @GetMapping("/{textId}")
    public ResponseEntity<Text> getTextWithId(@PathVariable int textId) {
        Optional<Text> foundText = textRepository.findById(textId);
        if (foundText.isPresent()) {
            return new ResponseEntity<>(foundText.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RestResponse> addText(@RequestBody Text newText) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        String textVal = newText.getText();
        if (textVal == null || textVal.isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = INVALID_TEXT_MSG;
        } else {
            newText.setId(null);
            textRepository.save(newText);

            status = HttpStatus.OK;
            message = "Text saved.";
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping
    public ResponseEntity<RestResponse> updateText(@RequestBody Text text) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        if (text.getText() == null || text.getText().isEmpty()) {
            status = HttpStatus.BAD_REQUEST;
            message = INVALID_TEXT_MSG;
        } else if (!textRepository.existsById(text.getId())) {
            status = HttpStatus.NOT_FOUND;
            message = String.format("Text with the ID '%d' doesn't exist.", text.getId());
        } else {
            status = HttpStatus.OK;
            message = "Text updated";
            textRepository.save(text);
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @DeleteMapping
    public ResponseEntity<RestResponse> deleteText(@RequestBody Text text) {
        RestResponse response = new RestResponse();
        HttpStatus status;
        String message;

        if (!textRepository.existsById(text.getId())) {
            status = HttpStatus.BAD_REQUEST;
            message = String.format(NOT_IN_DB_MSG, text.getId());
        } else {
            status = HttpStatus.OK;
            message = "Text deleted.";
            textRepository.deleteById(text.getId());
        }

        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}
