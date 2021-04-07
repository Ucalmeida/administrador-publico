package org.gestorpublico.dao;

import org.gestorpublico.entidade.Municipio;
import org.gestorpublico.entidade.Pessoa_Dependente;
import org.gestorpublico.entidade.Poder_Setor;
import org.gestorpublico.entidade.Pessoa;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends DAO<Pessoa> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Pessoa> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Pessoa> root;
	private Root<Pessoa> rootTuple;

	public PessoaDAO() {}
	
	public PessoaDAO(Session session) {
		super(session, Pessoa.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Pessoa.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Pessoa.class);
		rootTuple = query2.from(Pessoa.class);
	}

	public boolean jaExistePorCPF(String cpf) {
		return getPessoaPorCPF(cpf) != null;
	}

	public Pessoa getPessoa(Pessoa pessoa) {
		return (Pessoa) localizar(pessoa.getId());
	}

	public Pessoa getPessoaAcessoAdministrativoPermitidoPorLoginSenha(String login, String senha) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("acessaSistema"), true));
			predicates.add(builder.equal(root.get("acessoAdministrativo"), true));
			predicates.add(builder.equal(root.get("login"), login));
			predicates.add(builder.equal(root.get("senha"), senha));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Pessoa getPessoaAcessoPermitidoPorLoginSenha(String login, String senha) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("acessaSistema"), true));
			predicates.add(builder.equal(root.get("login"), login));
			predicates.add(builder.equal(root.get("senha"), senha));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Pessoa getPessoaPorCPF(String cpf) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("cpf"), cpf));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Tuple getPessoaTuplePorCPF(String cpf) {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));
			columns.add(rootTuple.get("cpf").alias("cpf"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(builder.equal(rootTuple.get("cpf"), cpf)))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Tuple getPessoaTuple(Pessoa pessoa) {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));

			return getSession().createQuery(
					query2.multiselect(columns)
					.where(builder.equal(rootTuple.get("id"), pessoa.getId())))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
    }

	public List<Tuple> liste() {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));
			columns.add(rootTuple.get("cpf").alias("cpf"));
			columns.add(builder.function("date_format", Long.class, rootTuple.get("dataNascimento"), builder.literal("%d/%m/%Y")).alias("dataNascimento"));
			columns.add(rootTuple.get("celular").alias("celular"));

			return getSession().createQuery(
					query2.multiselect(columns))
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

    public List<Tuple> litePessoasPorNome(String nome) {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));
			columns.add(rootTuple.get("cpf").alias("cpf"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(builder.like(rootTuple.get("nome"), "%"+nome+"%")))
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
    }

//	public List<Tuple> listePessoasPorAtivo(boolean ativo) {
//		try {
//			List<Predicate> predicates = new ArrayList<Predicate>();
//			predicates.add(builder.equal(rootTuple.get("ativo"), ativo));
//
//			List<Selection<?>> columns = new ArrayList<Selection<?>>();
//			columns.add(rootTuple.<Integer>get("id").alias("id"));
//			columns.add(rootTuple.<String>get("nome").alias("nome"));
//			columns.add(rootTuple.join("poderSetor").join("setor").<String>get("nome").alias("setor"));
////			columns.add(builder.concat(rootTuple.join("poderSetor").join("setor").get("nome"), builder.selectCase()
////					.when(builder.isNull(rootTuple.join("poderSetor").get("setorPai")), "")
////					.otherwise(builder.concat(rootTuple.join("poderSetor").join("setorPai").join("setor").get("nome"),"|"))));
//
//			return getSession().createQuery(
//					query2.multiselect(columns)
//							.where(predicates.toArray(new Predicate[0]))
//							.orderBy(builder.asc(rootTuple.get("nome"))))
//					.getResultList();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<>();
//		}
//	}
}
