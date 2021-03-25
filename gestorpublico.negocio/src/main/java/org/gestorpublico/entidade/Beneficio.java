package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames="nm_nome", name="nome")
    },
    indexes = {
        @Index(columnList = "nu_valor", name = "valor")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Beneficio implements Serializable, Comparable<Beneficio> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Type(type = "text")
    @Column(name = "tx_descricao")
    private String descricao;

    @Column(name = "nu_valor", nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal valor;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_poderSetor", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Setor_Beneficio"))
    private Poder_Setor poderSetor;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Beneficio o) {
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

    public String getValorFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(valor);
    }

    public void setValor(String valor) {
        this.valor = CassUtil.converterNumeroStringPtBrParaBigDecimal(valor);
    }
}
