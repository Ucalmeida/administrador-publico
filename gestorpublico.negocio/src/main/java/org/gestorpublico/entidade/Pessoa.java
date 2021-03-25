package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Entity
@Table(
	indexes = {
		@Index(columnList = "nm_nome", name = "nome"),
		@Index(columnList = "dt_nascimento", name = "dataNascimento"),
		@Index(columnList = "nm_cpf", name = "cpf")
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

	@Column(name = "dt_nascimento", nullable=false, columnDefinition="date")
	private LocalDate dataNascimento;

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
	@JoinColumn(name = "fk_sexo", foreignKey = @ForeignKey(name="FK_Sexo_Pessoa"))
	private Sexo sexo;
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
}
