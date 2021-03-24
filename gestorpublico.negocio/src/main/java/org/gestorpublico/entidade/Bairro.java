package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Bairro implements Serializable, Comparable<Bairro> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_municipio", nullable=false, foreignKey=@ForeignKey(name="FK_Municipio_Bairro"))
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name="fk_tipo", nullable=false, foreignKey=@ForeignKey(name="FK_Tipo_Bairro"))
    private Tipo tipo;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Bairro o) {
        int compare = this.municipio.compareTo(o.getMunicipio());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
