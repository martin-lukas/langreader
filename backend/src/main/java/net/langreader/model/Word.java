package net.langreader.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "words")
@Getter @Setter @NoArgsConstructor @ToString
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @Enumerated
    @Column(columnDefinition = "int")
    private WordType type;
}
