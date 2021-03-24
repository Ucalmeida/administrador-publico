package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_cnpj", name = "cnpj"),
        @Index(columnList = "bl_ativo", name = "ativo")
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
        int compare = this.poder.compareTo(o.getPoder());
        compare = compare != 0 ? compare : setor.compareTo(o.getSetor());
        return compare != 0 ? compare : (setorPai == null ? 0 : setorPai.getId().compareTo(o.getSetorPai().getId()));
    }

    // ****************** GETs e SETs ***************************************
    public String getCnpjFormatado() {
        return CassUtil.getCNPJFormatado(cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = CassUtil.removerMascara(cnpj);
    }

    public String getAtivoFormatado() {
        return ativo ? "Sim" : "Não";
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade == null || finalidade.trim().isEmpty() ? null : finalidade.trim();
    }

}
