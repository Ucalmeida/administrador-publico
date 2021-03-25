package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome"),
        @Index(columnList = "nm_link", name = "link"),
        @Index(columnList = "bl_menu", name = "menu"),
        @Index(columnList = "bl_padrao", name = "padrao"),
        @Index(columnList = "bl_liberavel", name = "liberavel")
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

    @Column(name = "nm_link", length = 150)
    private String link;

    @Column(name = "nm_icone", length = 50, nullable = false, columnDefinition = "varchar(50) default 'fas fa-caret-right'")
    private String icone;

    @Column(name = "bl_menu", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean menu;

    @Column(name = "bl_padrao", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean padrao;

    @Column(name = "bl_liberavel", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean liberavel;

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

    public void setLink(String link) {
        this.link = link == null || link.trim().isEmpty() ? null : link.trim();
    }
}
