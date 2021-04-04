package org.gestorpublico.dao;

import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Dependente;
import org.gestorpublico.entidade.Tipo;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pessoa_DependenteDAO extends DAO<Pessoa_Dependente> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Pessoa_Dependente> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Pessoa_Dependente> root;
	private Root<Pessoa_Dependente> rootTuple;

	public Pessoa_DependenteDAO() {}

	public Pessoa_DependenteDAO(Session session) {
		super(session, Pessoa_Dependente.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Pessoa_Dependente.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Pessoa_Dependente.class);
		rootTuple = query2.from(Pessoa_Dependente.class);
	}

	public Pessoa_Dependente getDependente(Pessoa_Dependente pessoaDependente) {
		return (Pessoa_Dependente) localizar(pessoaDependente.getId());
	}

	public boolean jaExistePorAscendenteDependenteTipoDependente(Pessoa ascendente, Pessoa dependente, Tipo tipoDependente) {
		return getDependentePorAscendenteDependenteTipoDependente(ascendente, dependente, tipoDependente) != null;
	}

	public Pessoa_Dependente getDependentePorAscendenteDependenteTipoDependente(Pessoa ascendente, Pessoa dependente, Tipo tipoDependente) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("ascendente"), ascendente));
			predicates.add(builder.equal(root.get("dependente"), dependente));
			predicates.add(builder.equal(root.get("tipoDependente"), tipoDependente));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Dependente> listarAtivosPorAscendente(Pessoa ascendente) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("ativo"), true));
			predicates.add(builder.equal(root.get("ascendente"), ascendente));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Tuple> listeDependentesPorAscendente(Pessoa pessoa) {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.join("dependente").<Integer>get("id").alias("id"));
			columns.add(rootTuple.join("dependente").<String>get("nome").alias("nome"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(builder.equal(rootTuple.get("ascendente"), pessoa)))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}
}
