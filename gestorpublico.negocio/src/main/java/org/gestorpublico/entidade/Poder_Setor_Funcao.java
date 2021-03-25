package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames={"fk_poderSetor","fk_funcao"})
    },
    indexes = {
        @Index(columnList = "nu_salario", name = "salario"),
        @Index(columnList = "in_cargaHoraria", name = "cargaHoraria")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Poder_Setor_Funcao implements Serializable, Comparable<Poder_Setor_Funcao> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nu_salario", nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal salario;

    @Column(name = "in_cargaHoraria", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "in_vagas", nullable = false)
    private Integer vagas;

    @Column(name = "tx_descricaoAtividade")
    private String descricaoAtividade;

    // **************************** RELACIONAMENTOS *************************
    @ManyToMany
    @JoinTable(name="Pessoa_Setor_Funcao_Acao",
            joinColumns = {@JoinColumn(name = "fk_poderSetorFuncao", foreignKey = @ForeignKey(name = "FK_Poder_Setor_Funcao_Poder_Setor_Funcao_Acao"))},
            inverseJoinColumns = {@JoinColumn(name = "fk_moduloAcao", foreignKey = @ForeignKey(name = "FK_Modulo_Acao_Poder_Setor_Funcao_Acao"))})
    private List<Modulo_Acao> acoes;

    @ManyToOne
    @JoinColumn(name="fk_poderSetor", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Setor_Poder_Setor_Pessoa"))
    private Poder_Setor poderSetor;

    @ManyToOne
    @JoinColumn(name="fk_funcao", nullable = false, foreignKey=@ForeignKey(name="FK_Funcao_Poder_Setor_Pessoa"))
    private Funcao funcao;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Poder_Setor_Funcao o) {
        int compare = this.poderSetor.getId().compareTo(o.getPoderSetor().getId());
        return compare != 0 ? compare : funcao.getNome().compareTo(o.getFuncao().getNome());
    }

    // ****************** GETs e SETs ***************************************
    public String getSalarioFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(salario);
    }

    public void setSalario(String salario) {
        this.salario = CassUtil.converterNumeroStringPtBrParaBigDecimal(salario);
    }
}
