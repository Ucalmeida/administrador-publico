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
public class Edificio implements Serializable, Comparable<Edificio> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_bairro", nullable=false, foreignKey=@ForeignKey(name="FK_Bairro_Edificio"))
    private Bairro bairro;

    @ManyToOne
    @JoinColumn(name="fk_condominio", foreignKey=@ForeignKey(name="FK_Condominio_Edificio"))
    private Condominio condominio;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Edificio o) {
        int compare = this.bairro.compareTo(o.getBairro());
        compare = compare != 0 ? compare : (condominio == null ? 0 : condominio.getId().compareTo(o.getCondominio().getId()));
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
