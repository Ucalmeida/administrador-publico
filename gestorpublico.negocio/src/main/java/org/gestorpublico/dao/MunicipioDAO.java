package org.gestorpublico.dao;

import org.gestorpublico.entidade.Municipio;
import org.gestorpublico.entidade.UF;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO extends DAO<Municipio> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Municipio> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Municipio> root;
	private Root<Municipio> rootTuple;

	public MunicipioDAO() {}

	public MunicipioDAO(Session session) {
		super(session, Municipio.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Municipio.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Municipio.class);
		rootTuple = query2.from(Municipio.class);
	}

	public Municipio getMunicipioPorNomeUFNomeMunicipio(String nomeUF, String nomeMunicipio) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.join("uf").get("nome"), nomeUF));
			predicates.add(builder.equal(root.get("nome"), nomeMunicipio));

			return getSession().createQuery(
					query.select(root)
							.where(predicates.toArray(new Predicate[0]))
							.orderBy(builder.asc(root.get("nome"))))
					.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Tuple> listeMunicipioPorUF(UF uf) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("uf"), uf));

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

	public List<Tuple> listeMunicipioPorNomeUF(String nomeUF) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.join("uf").get("nome"), nomeUF));

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
