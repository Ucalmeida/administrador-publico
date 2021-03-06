package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(columnList = "dt_alocacao", name = "dataAlocacao"),
        @Index(columnList = "dt_desalocacao", name = "dataDesalocacao"),
        @Index(columnList = "bl_acumulo", name = "acumulo")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Poder_Setor_Pessoa implements Serializable, Comparable<Poder_Setor_Pessoa> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dt_alocacao", nullable = false)
    private LocalDate dataAlocacao;

    @Column(name = "dt_desalocacao")
    private LocalDate dataDesalocacao;

    @Column(name = "bl_acumulo", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean acumulo;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_poderSetor", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Setor_Poder_Setor_Pessoa"))
    private Poder_Setor poderSetor;

    @ManyToOne
    @JoinColumn(name="fk_pessoa", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Poder_Setor_Pessoa"))
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name="fk_funcao", nullable = false, foreignKey=@ForeignKey(name="FK_Funcao_Poder_Setor_Pessoa"))
    private Funcao funcao;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Poder_Setor_Pessoa o) {
        int compare = this.poderSetor.getId().compareTo(o.getPoderSetor().getId());
        compare = compare != 0 ? compare : pessoa.getNome().compareTo(o.getPessoa().getNome());
        return compare != 0 ? compare : funcao.getNome().compareTo(o.getFuncao().getNome());
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
        return acumulo ? "Sim" : "N??o";
    }

}
