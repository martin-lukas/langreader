package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository
        extends JpaRepository<Word, Long> {

    Word findByWord(String word);

    boolean existsByWord(String word);
}
