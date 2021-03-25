package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Modulo_Acao implements Serializable, Comparable<Modulo_Acao> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    // **************************** RELACIONAMENTOS *************************
    @ManyToMany
    @JoinTable(name="Modulo_Acao_Acao_Precedente",
            joinColumns = {@JoinColumn(name = "fk_acaoDependente", foreignKey = @ForeignKey(name = "FK_Modulo_Acao_Modulo_ACao_Acao_Precedente_acaoDependente"))},
            inverseJoinColumns = {@JoinColumn(name = "fk_acaoPrecedente", foreignKey = @ForeignKey(name = "FK_Modulo_Acao_Modulo_ACao_Acao_Precedente_acao"))})
    private List<Modulo_Acao> acoesPrecedentes;

    @ManyToOne
    @JoinColumn(name="fk_modulo", nullable=false, foreignKey=@ForeignKey(name="FK_Modulo_Modulo_Acao"))
    private Modulo modulo;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Modulo_Acao o) {
        int compare = this.modulo.getId().compareTo(o.getModulo().getId());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

}
