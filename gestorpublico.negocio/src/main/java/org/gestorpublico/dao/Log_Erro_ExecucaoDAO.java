package org.gestorpublico.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

public class Log_Erro_ExecucaoDAO extends DAO<Log_Erro_Execucao> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Log_Erro_Execucao> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Log_Erro_Execucao> root;
	
	public Log_Erro_ExecucaoDAO() {}
	
	public Log_Erro_ExecucaoDAO(Session session) {
		super(session, Log_Erro_Execucao.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Log_Erro_Execucao.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Log_Erro_Execucao.class);
	}
	
	public boolean jaExiste(Log_Erro_Execucao logErroExecucao) {
		return localizar(logErroExecucao.getId()) != null;
	}
	
	public Log_Erro_Execucao getLog_Erro_Execucao(Log_Erro_Execucao logErroExecucao) {
		return (Log_Erro_Execucao) localizar(logErroExecucao.getId());
	}
	
	public List<Log_Erro_Execucao> listarLogPorPeriodo(LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.between(root.get("dataHoraCadastro"), inicio, CassUtil.getDataIncrementada(termino, 1)));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

//	public List<Log_Erro_Execucao> listarLogPorPeriodo(Calendar dataInicio, Calendar dataTermino) {
//		Calendar dtTermino = Calendar.getInstance();
//		dtTermino.setTime(dataTermino.getTime());
//		dtTermino = CassUtil.getDataHoraIncrementadaComHoraZerada(dtTermino, 1);
//		
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.add(Restrictions.between("dataHoraCadastro", dataInicio, dtTermino))
//				.list();
//	}

	public List<Log_Erro_Execucao> listarLogPorActionPeriodo(String action, LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("action"), action));
			predicates.add(builder.between(root.get("dataHoraCadastro"), inicio, CassUtil.getDataIncrementada(termino, 1)));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

//	public List<Log_Erro_Execucao> listarLogPorActionPeriodo(String action, Calendar dataInicio, Calendar dataTermino) {
//		Calendar dtTermino = Calendar.getInstance();
//		dtTermino.setTime(dataTermino.getTime());
//		dtTermino = CassUtil.getDataHoraIncrementadaComHoraZerada(dtTermino, 1);
//		
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.add(Restrictions.eq("action", action))
//				.add(Restrictions.between("dataHoraCadastro", dataInicio, dtTermino))
//				.list();
//	}

	public List<Log_Erro_Execucao> listarLogPorErroPeriodo(String erro, LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("erro"), erro));
			predicates.add(builder.between(root.get("dataHoraCadastro"), inicio, CassUtil.getDataIncrementada(termino, 1)));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

//	public List<Log_Erro_Execucao> listarLogPorErroPeriodo(String erro, Calendar dataInicio, Calendar dataTermino) {
//		Calendar dtTermino = Calendar.getInstance();
//		dtTermino.setTime(dataTermino.getTime());
//		dtTermino = CassUtil.getDataHoraIncrementadaComHoraZerada(dtTermino, 1);
//		
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.add(Restrictions.eq("erro", erro))
//				.add(Restrictions.between("dataHoraCadastro", dataInicio, dtTermino))
//				.list();
//	}

	public List<Log_Erro_Execucao> listarLogErroExecucaoPorAction(String action) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("action"), action));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}
	
//	public List<Log_Erro_Execucao> listarLogErroExecucaoPorAction(String action) {
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.add(Restrictions.eq("action", action))
//				.list();
//	}
	
	public List<Log_Erro_Execucao> listarLogErroExecucaoPorErro(String erro) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("erro"), erro));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}
	
//	public List<Log_Erro_Execucao> listarLogErroExecucaoPorErro(String erro) {
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.add(Restrictions.eq("erro", erro))
//				.list();
//	}
	
	public List<Log_Erro_Execucao> listarLogsErroExecucao() {
		try {
			return getSession().createQuery(query.select(root))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

//	public List<Log_Erro_Execucao> listarLogsErroExecucao() {
//		return getSession().createCriteria(Log_Erro_Execucao.class)
//				.list();
//	}

}
