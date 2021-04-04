package org.gestorpublico.dao;

import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Poder_Setor;
import org.gestorpublico.entidade.Servico;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficioDAO extends DAO<Beneficio> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Beneficio> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Beneficio> root;
	private Root<Beneficio> rootTuple;

	public BeneficioDAO() {}

	public BeneficioDAO(Session session) {
		super(session, Beneficio.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Beneficio.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Beneficio.class);
		rootTuple = query2.from(Beneficio.class);
	}

	public boolean jaExistePorPoderSetorNomeBeneficio(Poder_Setor poderSetor, String nomeBeneficio) {
		return getServicoPorPoderSetorNomeBeneficio(poderSetor, nomeBeneficio) != null;
	}

	public Beneficio getBeneficio(Beneficio beneficio) {
		return (Beneficio) localizar(beneficio.getId());
	}

	public Beneficio getServicoPorPoderSetorNomeBeneficio(Poder_Setor poderSetor, String nomeBeneficio) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("poderSetor"),poderSetor));
			predicates.add(builder.equal(root.get("nome"), nomeBeneficio));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Beneficio> listarAtivas() {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("ativo"), true));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Beneficio> listar() {
		try {
			return getSession().createQuery(query.select(root))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

	public List<Tuple> listeBeneficiosPorAtivo(boolean ativo) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("ativo"), ativo));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));
			columns.add(rootTuple.join("poderSetor").join("setor").<String>get("nome").alias("setor"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(predicates.toArray(new Predicate[0]))
							.orderBy(builder.asc(rootTuple.get("nome"))))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
