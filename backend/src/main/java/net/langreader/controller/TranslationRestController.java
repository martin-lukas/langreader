package net.langreader.controller;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import net.langreader.dao.LangRepository;
import net.langreader.model.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/translate")
public class TranslationRestController {
    @Value("${langreader.app.googleApiKey}")
    private String googleApiKey;

    @Autowired
    private LangRepository langRepository;

    @GetMapping("/{langId}")
    public ResponseEntity<String> getTranslation(
            @PathVariable("langId") int langId,
            @RequestParam(value = "word") String word) {
        Optional<Language> langOpt = langRepository.findById(langId);
        if (langOpt.isPresent()) {
            Translate translate = TranslateOptions.newBuilder()
                    .setApiKey(googleApiKey).build().getService();
            Translation translation = translate.translate(
                    word,
                    Translate.TranslateOption.sourceLanguage(langOpt.get().getCode()),
                    Translate.TranslateOption.targetLanguage("EN"));
            return new ResponseEntity<>(translation.getTranslatedText(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
