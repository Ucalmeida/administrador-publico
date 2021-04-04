package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
    indexes = {
        @Index(columnList = "dh_cadastro", name = "dataHoraCadastro"),
        @Index(columnList = "bl_ativo", name = "ativo")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Pessoa_Dependente implements Serializable, Comparable<Pessoa_Dependente> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dh_cadastro", nullable = false)
    private LocalDateTime dataHoraCadastro = LocalDateTime.now();

    @Column(name = "bl_ativo", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean ativo;

    @Column(name = "bl_tutelaJudicial", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean tutelaJudicial;

    @Column(name = "nm_numeroDecisaoJudicial", length = 50)
    private String numeroDecisaoJudicial;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_ascendente", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Dependente_ascendente"))
    private Pessoa ascendente;

    @ManyToOne
    @JoinColumn(name="fk_dependente", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Dependente_dependente"))
    private Pessoa dependente;

    @ManyToOne
    @JoinColumn(name="fk_tipoDependente", foreignKey=@ForeignKey(name="FK_Tipo_Pessoa_Dependente"))
    private Tipo tipoDependente;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Pessoa_Dependente o) {
        int compare = this.ascendente.compareTo(o.getAscendente());
        return compare != 0 ? compare : dependente.getNome().compareTo(o.getDependente().getNome());
    }

    // ****************** GETs e SETs ***************************************
    public String getDataHoraCadastroFormatada() {
        return CassUtil.getDataHoraFormatada(dataHoraCadastro);
    }

    public String getAtivoFormatado() {
        return ativo ? "Sim" : "Não";
    }

    public String getTutelaJudicialFormatada() {
        return tutelaJudicial ? "Sim" : "Não";
    }

    public void setNumeroDecisaoJudicial(String numeroDecisaoJudicial) {
        this.numeroDecisaoJudicial = numeroDecisaoJudicial == null || numeroDecisaoJudicial.trim().isEmpty() ? null : numeroDecisaoJudicial.trim();
    }
}
