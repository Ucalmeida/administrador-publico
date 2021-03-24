package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name="nm_nome", columnNames="nm_nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Setor implements Serializable, Comparable<Setor> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 150,nullable = false)
    private String nome;

    // **************************** RELACIONAMENTOS *************************

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Setor o) {
        return nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
