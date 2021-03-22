package org.gestorpublico.dao;

import org.gestorpublico.entidade.Pessoa;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PessoaDAO extends DAO<Pessoa> {

	public PessoaDAO() {}

	public PessoaDAO(Session session) {
		super(session, Pessoa.class);
	}

	public boolean jaExiste(Pessoa pessoa) {
		return localizar(pessoa.getId()) != null;
	}

	public boolean jaExistePorCPF(String cpf) {
		return getPessoaPorCPF(cpf) != null;
	}

	public boolean jaExistePorCPF(Pessoa pessoa) {
		return getPessoaPorCPF(pessoa.getCpf()) != null;
	}

	public Pessoa getPessoaPorID(Integer pessoa) {
		return localizar(pessoa);
	}
	
	public Pessoa getPessoa(Pessoa pessoa) {
		return localizar(pessoa.getId());
	}

	public Pessoa getPessoaAcessoPermitido(Pessoa pessoa) {
		return (Pessoa) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("acessaSistema", true))
				.add(Restrictions.idEq(pessoa.getId()))
				.uniqueResult();
	}

	public Pessoa getPessoaAcessoPermitidoPorLoginSenha(String login, String senha) {
		return (Pessoa) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("acessaSistema", true))
				.add(Restrictions.eq("login", login))
				.add(Restrictions.eq("senha", senha))
				.uniqueResult();
	}

	public Pessoa getPessoaPorCPF(Pessoa pessoa) {
		return (Pessoa) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("cpf", pessoa.getCpf()))
				.uniqueResult();
	}

	public Pessoa getPessoaPorCPF(String cpf) {
		return (Pessoa) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("cpf", cpf))
				.uniqueResult();
	}
	
	public Pessoa getPessoaPorCelular(String celular) {
		return (Pessoa) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("celular", celular))
				.uniqueResult();
	}

//	public List<Map<String, Object>> listePessoasPorNome(String nome) {
//		String listarPessoasPorNome = getQuery("listarPessoasPorNome");
//		return getSession().createSQLQuery(listarPessoasPorNome)
//				.setParameter("nome", nome)
//				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
//				.list();
		/*return getSession().createSQLQuery("CALL listarPessoasPorNome(:nome)")
				.setParameter("nome", nome)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
				.list();*/
//	}
	
	public List<Pessoa> listarPessoasPorNome(String nome) {
		return getSession().createCriteria(Pessoa.class)
				.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE))
				.addOrder(Order.asc("nome"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}
	
//	public List<Pessoa> listarPessoasDosSetoresPorNomeUnidade(String nome, List<Unidade> unidades) {
//		return getSession().createCriteria(Pessoa_Setor_Funcao.class)
//				.createAlias("pessoa", "pessoa")
//				.createAlias("unidadeSetor", "unidadeSetor")
//				.add(Restrictions.in("pessoa.unidade", unidades))
//				.add(Restrictions.like("pessoa.nome", nome, MatchMode.ANYWHERE))
//				.setProjection(Projections.property("pessoa"))
//				.list();
//	}

//	public List<Pessoa> listarPessoasPorStatus(Status status) {
//		return getSession().createCriteria(Pessoa.class)
//				.add(Restrictions.eq("status", status))
//				.addOrder(Order.asc("nome"))
//				.list();
//	}

	public List<Pessoa> listarPessoasAcessoAtivo() {
		return getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("acessoSistema", true))
				.list();
	}

	public List<Pessoa> listarPessoas() {
		return getSession().createCriteria(Pessoa.class)
				.list();
	}

//	public boolean getPessoaPublicaPorPessoaSetorFuncao(Pessoa pessoa, Pessoa_Setor_Funcao psf) {
//		return getSession().createCriteria(Pessoa_Setor_Funcao.class, "psf")
//				.createAlias("psf.acoes", "acoes")
//				.add(Restrictions.eq("acoes.link", "bolNotaLiberarPublicacao"))
//				.add(Restrictions.eq("id", psf.getId()))
//				.uniqueResult() != null;
//	}
//
//	public boolean getPessoaHomologaPorPessoaSetorFuncao(Pessoa pessoa, Pessoa_Setor_Funcao psf) {
//		return getSession().createCriteria(Pessoa_Setor_Funcao.class, "psf")
//				.createAlias("psf.acoes", "acoes")
//				.add(Restrictions.eq("acoes.link", "homologarModalForm"))
//				.add(Restrictions.eq("id", psf.getId()))
//				.uniqueResult() != null;
//	}
	public List<Pessoa> listarPessoaPorNome(String nome) {
		return getSession().createCriteria(Pessoa.class)
				.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
	}


	public Pessoa login(String login, String senha) {
		return Pessoa.mapper(getSession().createCriteria(Pessoa.class, "pessoa")
				.add(Restrictions.eq("login", login))
				.add(Restrictions.eq("senha", senha))
				.createAlias("sexo", "sexo")
				.setProjection(Projections.projectionList()
					.add(Projections.property("cpf"), "cpf")
					.add(Projections.property("nome"), "nome")
					.add(Projections.property("dataNascimento"), "dataNascimento")
					.add(Projections.property("telefone"), "telefone")
					.add(Projections.property("email"), "email")
					.add(Projections.property("emailPessoal"), "emailPessoal")
					.add(Projections.property("militar"), "militar")
					.add(Projections.property("sexo"), "sexo")
					.add(Projections.property("pessoa.acoes"), "acoes")
				)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
				.uniqueResult());
	}

	public Map<String, Object> teste(Integer id) {
		return  (Map<String, Object>) getSession().createCriteria(Pessoa.class)
				.add(Restrictions.eq("id", id))
				.createAlias("sexo", "sexo")
				.setProjection(Projections.projectionList()
					.add(Projections.id(), "id")
					.add(Projections.property("cpf"), "cpf")
					.add(Projections.property("nome"), "nome")
					.add(Projections.property("dataNascimento"), "dataNascimento")
					.add(Projections.property("telefone"), "telefone")
					.add(Projections.property("email"), "email")
					.add(Projections.property("emailPessoal"), "emailPessoal")
					.add(Projections.property("militar"), "militar")
					.add(Projections.property("sexo"), "sexo")

				)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
				.uniqueResult();
	}

}
