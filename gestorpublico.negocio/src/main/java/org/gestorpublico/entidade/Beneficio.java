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
        @UniqueConstraint(name="nm_nome", columnNames="nm_nome")
    },
    indexes = {
        @Index(columnList = "nu_valor", name = "nu_valor")
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

    @Column(name = "nm_nome", nullable = false)
    private String nome;

    @Type(type = "text")
    @Column(name = "tx_descricao")
    private String descricao;

    @Column(name = "nu_valor", nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal valor;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_poderSetor", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Setor_Poder_Setor_Pessoa"))
    private Poder_Setor poderSetor;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Beneficio o) {
        int compare = this.poderSetor.getId().compareTo(o.getPoderSetor().getId());
        return compare != 0 ? compare : nome.compareTo(o.getFuncao().getNome());
    }

    // ****************** GETs e SETs ***************************************
    public String getDataAlocacaoFormatada() {
        return CassUtil.getDataFormatada(dataAlocacao);
    }

    public void setDataAlocacao(String dataAlocacao) {
        this.dataAlocacao = CassUtil.converterDataStringParaLocalDate(dataAlocacao);
    }

    public String getDataDesalocacaoFormatada() {
        return CassUtil.getDataFormatada(dataDesalocacao);
    }

    public void setDataDesalocacao(String dataDesalocacao) {
        this.dataDesalocacao = CassUtil.converterDataStringParaLocalDate(dataDesalocacao);
    }

    public String getAcumuloFormatado() {
        return acumulo ? "Sim" : "Não";
    }

}
