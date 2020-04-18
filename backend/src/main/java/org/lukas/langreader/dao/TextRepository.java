package org.lukas.langreader.dao;

import org.lukas.langreader.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Integer> {

    boolean existsById(int id);

    void deleteById(int id);
}
