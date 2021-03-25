package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome"),
        @Index(columnList = "nm_latitude", name = "latitude"),
        @Index(columnList = "nm_longitude", name = "longitude")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Ponto_Referencia implements Serializable, Comparable<Ponto_Referencia> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "nm_latitude", length = 45)
    private String latitude;

    @Column(name = "nm_longitude", length = 45)
    private String longitude;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_bairro", nullable=false, foreignKey=@ForeignKey(name="FK_Bairro_Ponto_Referencia"))
    private Bairro bairro;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Ponto_Referencia o) {
        int compare = this.bairro.compareTo(o.getBairro());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
