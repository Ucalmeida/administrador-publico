package org.gestorpublico.dao;

import org.gestorpublico.entidade.Bairro;
import org.gestorpublico.entidade.Municipio;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class BairroDAO extends DAO<Bairro> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Bairro> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Bairro> root;
	private Root<Bairro> rootTuple;

	public BairroDAO() {}
	
	public BairroDAO(Session session) {
		super(session, Bairro.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Bairro.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Bairro.class);
		rootTuple = query2.from(Bairro.class);
	}

	public List<Tuple> listeBairroPorNomeMunicipio(String nomeMunicipio) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.join("municipio").get("nome"), nomeMunicipio));

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

	public List<Tuple> listeBairroPorMunicipio(Municipio municipio) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("municipio"), municipio));

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

	public List<Tuple> listeBairroPorNomeUFNomeMunicipio(String nomeUF, String nomeMunicipio) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			Join<Object, Object> municipio = rootTuple.join("municipio");
			predicates.add(builder.equal(municipio.join("uf").get("nome"), nomeUF));
			predicates.add(builder.equal(municipio.get("nome"), nomeMunicipio));

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
