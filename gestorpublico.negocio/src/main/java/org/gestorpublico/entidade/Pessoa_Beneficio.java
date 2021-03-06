package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Entity
@Table(
    indexes = {
            @Index(columnList = "dh_cadastro", name = "dataHoraCadastro"),
            @Index(columnList = "dt_concessao", name = "dataConcessao")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Pessoa_Beneficio implements Serializable, Comparable<Pessoa_Beneficio> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dh_cadastro", nullable = false)
    private LocalDateTime dataHoraCadastro = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    @Column(name = "dt_concessao")
    private LocalDate dataConcessao;

    @Column(name = "nu_valorUnitario", columnDefinition = "decimal(10,2)")
    private BigDecimal valorUnitario;

    @Column(name = "in_quantidade")
    private Integer quantidade;

    @Column(name = "bl_autorizado", columnDefinition = "tinyint(1)")
    private Boolean autorizado;

    @Type(type = "text")
    @Column(name = "tx_despacho")
    private String despacho;

    @Type(type = "text")
    @Column(name = "tx_observacao")
    private String observacao;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_responsavel", foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Beneficio_responsavel"))
    private Pessoa responsavel;

    @ManyToOne
    @JoinColumn(name="fk_solicitante", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Beneficio_solicitante"))
    private Pessoa solicitante;

    @ManyToOne
    @JoinColumn(name="fk_beneficiado", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Beneficio_beneficiado"))
    private Pessoa beneficiado;

    @ManyToOne
    @JoinColumn(name="fk_beneficio", nullable = false, foreignKey=@ForeignKey(name="FK_Beneficio_Pessoa_Endereco"))
    private Beneficio beneficio;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Pessoa_Beneficio o) {
        int compare = this.beneficiado.compareTo(o.getBeneficiado());
        compare = compare != 0 ? compare : beneficio.compareTo(o.getBeneficio());
        compare = compare != 0 ? compare : dataConcessao.compareTo(o.getDataConcessao());
        return compare != 0 ? compare : dataHoraCadastro.compareTo(o.getDataHoraCadastro());
    }

    // ****************** GETs e SETs ***************************************
    public String getDataHoraCadastroFormatada() {
        return CassUtil.getDataHoraFormatada(dataHoraCadastro);
    }

    public String getValorUnitarioFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(valorUnitario);
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = CassUtil.converterNumeroStringPtBrParaBigDecimal(valorUnitario);
    }

    /**
     * Calcula o valor total do benef??cio multiplicando o valor unit??rio pela quantidade
     * @return BigDecimal
     */
    public BigDecimal getValorTotal() {
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }

    /**
     * Calcula o valor total do benef??cio multiplicando o valor unit??rio pela quantidade
     * @return String
     */
    public String getValorTotalFormatado() {
        return CassUtil.converterBigDecimalParaNumeroStringPtBr(valorUnitario.multiply(new BigDecimal(quantidade)));
    }

    public String getAutorizadoFormatado() {
        return autorizado == null ? "Aguardando despacho" : (autorizado ? "Sim" : "N??o");
    }

    public void setDespacho(String despacho) {
        this.despacho = despacho == null || despacho.trim().isEmpty() ? null : despacho.trim();
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}
