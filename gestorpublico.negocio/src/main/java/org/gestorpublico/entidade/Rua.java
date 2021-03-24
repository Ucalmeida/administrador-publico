package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Rua implements Serializable, Comparable<Rua> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "nm_cep", length = 8, nullable = false)
    private String cep;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_bairro", nullable=false, foreignKey=@ForeignKey(name="FK_Bairro_Rua"))
    private Bairro bairro;

    @ManyToOne
    @JoinColumn(name="fk_tipo", nullable=false, foreignKey=@ForeignKey(name="FK_Tipo_Rua"))
    private Tipo tipo;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Rua o) {
        int compare = this.bairro.compareTo(o.getBairro());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
