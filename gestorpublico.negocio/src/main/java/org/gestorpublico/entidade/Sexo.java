package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        indexes = {
            @Index(columnList = "nm_nome", name = "nome"),
            @Index(columnList = "nm_sigla", name = "sigla")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Sexo implements Serializable, Comparable<Sexo> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 10,nullable = false)
    private String nome;

    @Column(name = "nm_sigla", length = 1, nullable = false)
    private String sigla;

    // **************************** RELACIONAMENTOS *************************
    @OneToMany(mappedBy = "sexo")
    private List<Pessoa> pessoas;
    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************

    @Override
    public int compareTo(Sexo o) {
        return nome.compareTo(o.getNome());
    }
}
