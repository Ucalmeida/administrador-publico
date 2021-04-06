package org.gestorpublico.dao;

import org.gestorpublico.entidade.Bairro;
import org.gestorpublico.entidade.Ponto_Referencia;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Ponto_ReferenciaDAO extends DAO<Ponto_Referencia> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Ponto_Referencia> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Ponto_Referencia> root;
	private Root<Ponto_Referencia> rootTuple;

	public Ponto_ReferenciaDAO() {}

	public Ponto_ReferenciaDAO(Session session) {
		super(session, Ponto_Referencia.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Ponto_Referencia.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Ponto_Referencia.class);
		rootTuple = query2.from(Ponto_Referencia.class);
	}

	public List<Tuple> listePorBairro(Bairro bairro) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("bairro"), bairro));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));

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
