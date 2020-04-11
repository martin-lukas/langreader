package org.lukas.langreader.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "known_expressions")
@Getter @Setter @NoArgsConstructor @ToString
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String val;
//    private ExpressionType type;
}
