package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    indexes = {
        @Index(columnList = "dh_cadastro", name = "dataHoraCadastro"),
        @Index(columnList = "dt_inicio", name = "inicio"),
        @Index(columnList = "dt_termino", name = "termino"),
        @Index(columnList = "bl_autorizado", name = "autorizado")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Pessoa_Servico implements Serializable, Comparable<Pessoa_Servico> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dh_cadastro", nullable = false)
    private LocalDateTime dataHoraCadastro = LocalDateTime.now();

    @Column(name = "dt_inicio")
    private LocalDate dataInicio;

    @Column(name = "dt_termino")
    private LocalDate dataTermino;

    @Column(name = "nu_valorInicial", nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal valorInicial;

    @Column(name = "nu_valorFinal", columnDefinition = "decimal(10,2)")
    private BigDecimal valorFinal;

    @Column(name = "bl_autorizado", columnDefinition = "tinyint(1)")
    private Boolean autorizado;

    @Column(name = "tx_despacho")
    private String despacho;

    @Column(name = "tx_observacao")
    private String observacao;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_solicitante", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Servico"))
    private Pessoa solicitante;

    @ManyToOne
    @JoinColumn(name="fk_servico", nullable=false, foreignKey=@ForeignKey(name="FK_Servico_Pessoa_Servico"))
    private Servico servico;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Pessoa_Servico o) {
        int compare = this.solicitante.compareTo(o.getSolicitante());
        compare = compare != 0 ? compare : servico.compareTo(o.getServico());
        return compare != 0 ? compare : getDataHoraCadastro().compareTo(o.getDataHoraCadastro());
    }

    // ****************** GETs e SETs ***************************************
    public String getDataHoraCadastroFormatada() {
        return CassUtil.getDataHoraFormatada(dataHoraCadastro);
    }

    public String getValorInicialFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(valorInicial);
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = CassUtil.converterNumeroStringPtBrParaBigDecimal(valorInicial);
    }

    public String getValorFinalFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(valorFinal);
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = CassUtil.converterNumeroStringPtBrParaBigDecimal(valorFinal);
    }

    public String getAutorizadoFormatado() {
        return autorizado == null ? "" : (autorizado ? "Sim" : "Não");
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho == null || despacho.trim().isEmpty() ? null : despacho.trim();
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}
