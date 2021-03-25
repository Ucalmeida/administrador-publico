package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_numero", name = "numero")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Pessoa_Endereco implements Serializable, Comparable<Pessoa_Endereco> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_numero", length = 5, nullable = false, columnDefinition = "varchar(5) default 'SN'")
    private String numero;

    @Column(name = "nm_complemento", length = 50)
    private String complemento;

    @Column(name = "nm_latitude", length = 45)
    private String latitude;

    @Column(name = "nm_longitude", length = 45)
    private String longitude;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_pessoa", nullable=false, foreignKey=@ForeignKey(name="FK_Pessoa_Pessoa_Endereco"))
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name="fk_rua", nullable=false, foreignKey=@ForeignKey(name="FK_Rua_Pessoa_Endereco"))
    private Rua rua;

    @ManyToOne
    @JoinColumn(name="fk_pontoReferencia", foreignKey=@ForeignKey(name="FK_Ponto_Referencia_Pessoa_Endereco"))
    private Ponto_Referencia pontoreferencia;

    @ManyToOne
    @JoinColumn(name="fk_condominio", foreignKey=@ForeignKey(name="FK_Condominio_Pessoa_Endereco"))
    private Condominio condominio;

    @ManyToOne
    @JoinColumn(name="fk_edificio", foreignKey=@ForeignKey(name="FK_Edificio_Pessoa_Endereco"))
    private Edificio edificio;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Pessoa_Endereco o) {
        int compare = this.pessoa.compareTo(o.getPessoa());
        compare = compare != 0 ? compare : rua.compareTo(o.getRua());
        compare = compare != 0 ? compare : (pontoreferencia == null ? 0 : pontoreferencia.getId().compareTo(o.getPontoreferencia().getId()));
        compare = compare != 0 ? compare : (condominio == null ? 0 : condominio.getId().compareTo(o.getCondominio().getId()));
        compare = compare != 0 ? compare : (edificio == null ? 0 : edificio.getId().compareTo(o.getEdificio().getId()));
        return compare != 0 ? compare : numero.compareTo(o.getNumero());
    }

    // ****************** GETs e SETs ***************************************
    public void setNumero(String numero) {
        this.numero = numero == null || numero.trim().isEmpty() ? "SN" : numero.trim();
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento == null || complemento.trim().isEmpty() ? null : complemento.trim();
    }

}
