package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames="nm_nome", name="nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Objeto implements Serializable, Comparable<Objeto> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    // **************************** RELACIONAMENTOS *************************

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Objeto o) {
        return nome.compareTo(o.getNome());
    }

}
