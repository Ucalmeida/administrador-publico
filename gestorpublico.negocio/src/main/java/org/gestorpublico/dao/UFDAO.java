package org.gestorpublico.dao;

import org.gestorpublico.entidade.Objeto;
import org.gestorpublico.entidade.UF;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UFDAO extends DAO<UF> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<UF> query;
	private CriteriaQuery<Tuple> query2;
	private Root<UF> root;
	private Root<UF> rootTuple;

	public UFDAO() {}
	
	public UFDAO(Session session) {
		super(session, UF.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(UF.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(UF.class);
		rootTuple = query2.from(UF.class);
	}

	public List<Tuple> listeUFPorNomePais(String nomePais) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.join("pais").get("nome"), nomePais));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));
			columns.add(rootTuple.<String>get("sigla").alias("sigla"));

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
