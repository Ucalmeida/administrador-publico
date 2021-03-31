package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_cnpj", name = "cnpj"),
        @Index(columnList = "bl_ativo", name = "ativo")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class Poder_Setor implements Serializable, Comparable<Poder_Setor> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_cnpj", length = 14)
    private String cnpj;

    @Column(name = "nm_telefone", length = 11)
    private String telefone;

    @Column(name = "nm_telefone2", length = 11)
    private String telefone2;

    @Column(name = "nm_telefone3", length = 11)
    private String telefone3;

    @Type(type = "text")
    @Column(name = "tx_finalidade")
    private String finalidade;

    @Column(name = "bl_ativo", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean ativo;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_poder", nullable=false, foreignKey=@ForeignKey(name="FK_Poder_Poder_Setor"))
    private Poder poder;

    @ManyToOne
    @JoinColumn(name="fk_setor", nullable=false, foreignKey=@ForeignKey(name="FK_Setor_Poder_Setor"))
    private Setor setor;

    @ManyToOne
    @JoinColumn(name="fk_setorPai", foreignKey=@ForeignKey(name="FK_Poder_Setor_Poder_Setor"))
    private Poder_Setor setorPai;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Poder_Setor o) {
        int compare = this.poder.compareTo(o.getPoder());
        compare = compare != 0 ? compare : setor.compareTo(o.getSetor());
        return compare != 0 ? compare : (setorPai == null ? 0 : setorPai.getId().compareTo(o.getSetorPai().getId()));
    }

    @Override
    public String toString() {
        return (setorPai == null ? "" : setorPai.toString() + "|") + setor.getNome();
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
