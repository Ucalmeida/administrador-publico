package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames="nm_nome", name="nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Servico implements Serializable, Comparable<Servico> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Type(type = "text")
    @Column(name = "tx_descricao")
    private String descricao;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_poderSetor", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Setor_Servico"))
    private Poder_Setor poderSetor;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Servico o) {
        int compare = this.poderSetor.getId().compareTo(o.getPoderSetor().getId());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao == null || descricao.trim().isEmpty() ? null : descricao.trim();
    }
}
