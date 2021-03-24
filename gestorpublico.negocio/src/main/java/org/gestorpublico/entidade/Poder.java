package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Poder implements Serializable, Comparable<Poder> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 150, nullable = false)
    private String nome;

    @Column(name = "dt_fundacao")
    private LocalDate dataFuncacao;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_municipio", nullable=false, foreignKey=@ForeignKey(name="FK_Municipio_Poder"))
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name="fk_tipo", nullable=false, foreignKey=@ForeignKey(name="FK_Tipo_Poder"))
    private Tipo tipo;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Poder o) {
        int compare = this.municipio.compareTo(o.getMunicipio());
        compare = compare != 0 ? compare : this.tipo.compareTo(o.getTipo());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

    public String getDataFuncacaoFormatada() {
        return CassUtil.getDataFormatada(dataFuncacao);
    }

    public void setDataFuncacao(String dataFuncacao) {
        this.dataFuncacao = CassUtil.converterDataStringParaLocalDate(dataFuncacao);
    }

}
