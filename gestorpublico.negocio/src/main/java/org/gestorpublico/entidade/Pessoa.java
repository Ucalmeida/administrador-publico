package org.gestorpublico.entidade;

import lombok.*;
import org.gestorpublico.util.CassUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Entity
@Table(
	indexes = {
		@Index(columnList = "nm_nome", name = "nome"),
		@Index(columnList = "dt_nascimento", name = "dataNascimento"),
		@Index(columnList = "nm_cpf", name = "cpf"),
		@Index(columnList = "bl_acessoAdministrativo", name = "acessoAdministrativo")
	}
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Pessoa implements Serializable, Comparable<Pessoa> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "dt_nascimento", nullable=false)
	private LocalDate dataNascimento;

	@Column(name = "dt_falecimento", columnDefinition="date")
	private LocalDate dataFalecimento;

	@Column(name = "nm_nome", length=150, nullable = false)
	private String nome;

	@Column(name = "nm_cpf", length=11, nullable = false, unique=true)
	private String cpf;

	@Column(name = "nm_cartaoSus", length=15)
	private String cartaoSus;

	@Column(name = "nm_nis", length=15)
	private String nis;
	
	@Column(name = "nm_celular", length=11, nullable = false)
	private String celular;

	@Column(name = "nm_login", length=20, nullable=false)
	private String login;

	@Column(name = "nm_senha", length=42, nullable=false)
	private String senha;

	@Column(name = "nm_fcmToken", length=255)
	private String fcmToken;

	@Column(name="bl_postsLiberados", nullable=false, columnDefinition="tinyint default 1")
	private boolean postsLiberados;
	
	@Column(name="bl_postsMidiaLiberados", nullable=false, columnDefinition="tinyint default 1")
	private boolean postsMidiaLiberados;

	@Column(name="bl_acessaSistema", nullable=false, columnDefinition="tinyint default 1")
	private boolean acessaSistema;

	@Column(name="bl_auxilioAluguel", nullable=false, columnDefinition="tinyint default 0")
	private boolean auxilioAluguel;

	@Column(name="bl_bolsaFamilia", nullable=false, columnDefinition="tinyint default 0")
	private boolean bolsaFamilia;

	@Column(name="bl_vivo", nullable=false, columnDefinition="tinyint default 1")
	private boolean vivo;

	@Column(name="bl_acessoAdministrativo", nullable=false, columnDefinition="tinyint default 0")
	private boolean acessoAdministrativo;

	// **************************** RELACIONAMENTOS *************************
	@ManyToMany
	@JoinTable(name="Pessoa_Acao_Extra",
			joinColumns = {@JoinColumn(name = "fk_pessoa", foreignKey = @ForeignKey(name = "FK_Pessoa_Pessoa_Acao_Extra"))},
			inverseJoinColumns = {@JoinColumn(name = "fk_moduloAcao", foreignKey = @ForeignKey(name = "FK_Modulo_Acao_Pessoa_ACao_Extra"))})
	private List<Modulo_Acao> acoesExtras;

	@ManyToMany
	@JoinTable(name="Pessoa_Acao_Negada",
			joinColumns = {@JoinColumn(name = "fk_pessoa", foreignKey = @ForeignKey(name = "FK_Pessoa_Pessoa_Acao_Negada"))},
			inverseJoinColumns = {@JoinColumn(name = "fk_moduloAcao", foreignKey = @ForeignKey(name = "FK_Modulo_Acao_Pessoa_ACao_Negada"))})
	private List<Modulo_Acao> acoesNegadas;

	@ManyToOne
	@JoinColumn(name = "fk_mae", foreignKey = @ForeignKey(name="FK_Pessoa_Pessoa_mae"))
	private Pessoa mae;

	@ManyToOne
	@JoinColumn(name = "fk_pai", foreignKey = @ForeignKey(name="FK_Pessoa_Pessoa_pai"))
	private Pessoa pai;

	@ManyToOne
	@JoinColumn(name = "fk_sexo", nullable = false, foreignKey = @ForeignKey(name="FK_Sexo_Pessoa"))
	private Sexo sexo;

	@OneToMany(mappedBy = "ascendente")
	private List<Pessoa_Dependente> dependentes;

	// **************************** CONTRUTORES *****************************

	// ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
	@Override
	public int compareTo(Pessoa o) {
		return cpf.compareTo(o.getCpf());
	}

	// **************************** GETS e SETs *****************************
	public static Pessoa mapper(Object o) {
		Map<String, Object> map = (HashMap<String, Object>) o;
		Pessoa pessoa = new Pessoa();
		try {
			Field[] campos = pessoa.getClass().getDeclaredFields();
			for (Field campo : campos) {

				if (campo.getName().equals("serialVersionUID")) {
					continue;
				}

				String nomeSet = "set"+camelCase(campo.getName());
				Method method = pessoa.getClass().getMethod(nomeSet, campo.getType());
				try {
					method.invoke(pessoa, map.get(campo.getName()));
				} catch (Exception i) {
					continue;
				}
			}
		} catch (Exception e) {
			Logger logger = Logger.getLogger(pessoa.getClass().getSimpleName());
			logger.info("<<<< Erro no mapper de Pessoa >>>>");
			return pessoa;
		}
		return pessoa;
	}

	private static String camelCase(String in) {
		String substring = in.substring(0, 1);
		return substring.toUpperCase()+in.substring(1);
	}

	public void setCpf(String cpf) {
		this.cpf = CassUtil.removerMascara(cpf);
	}

	public String getCpfFormatado() {
		return CassUtil.getCPFFormatado(cpf);
	}

	public String getDataNascimentoFormatada() {
		return CassUtil.getDataFormatada(dataNascimento);
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = CassUtil.converterDataStringParaLocalDate(dataNascimento);
	}

	public String getDataFalecimentoFormatada() {
		return CassUtil.getDataFormatada(dataFalecimento);
	}

	public void setDataFalecimento(String dataFalecimento) {
		this.dataFalecimento = CassUtil.converterDataStringParaLocalDate(dataFalecimento);
	}

	public String getCelularFormatado() {
		return CassUtil.getCepFormatado(celular);
	}

	public void setCelular(String celular) {
		this.celular = CassUtil.removerMascara(celular);
	}

	public String getPrimeiroNome() {
		return nome.substring(0, nome.indexOf(" "));
	}

	public boolean isMenorIdade() {
		return LocalDate.now().getYear() - dataNascimento.getYear() < 18;
	}
}
