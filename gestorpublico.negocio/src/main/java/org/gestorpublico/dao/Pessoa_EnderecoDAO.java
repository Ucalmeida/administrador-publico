package org.gestorpublico.dao;

import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Endereco;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pessoa_EnderecoDAO extends DAO<Pessoa_Endereco> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Pessoa_Endereco> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Pessoa_Endereco> root;
	private Root<Pessoa_Endereco> rootTuple;

	public Pessoa_EnderecoDAO() {}

	public Pessoa_EnderecoDAO(Session session) {
		super(session, Pessoa_Endereco.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Pessoa_Endereco.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Pessoa_Endereco.class);
		rootTuple = query2.from(Pessoa_Endereco.class);
	}

	public Pessoa_Endereco getBeneficio(Pessoa_Endereco pessoaEndereco) {
		return (Pessoa_Endereco) localizar(pessoaEndereco.getId());
	}
}
