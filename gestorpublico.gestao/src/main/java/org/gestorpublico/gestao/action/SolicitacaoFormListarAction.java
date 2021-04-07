package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ParentPackage("default")
public class SolicitacaoFormListarAction extends PadraoAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private List<Tuple> beneficios;
    private List<Tuple> servicos;

    @Action(value="solicitacoes",
        results={
            @Result(name="ok", location="solicitacaoFormLista.jsp"),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            beneficios = new Pessoa_BeneficioDAO(session).listeAguardandoDespacho();
            servicos = new Pessoa_ServicoDAO(session).listeAguardandoDespacho();

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            Session sessao = HibernateUtil.getSession();
            new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(getActionName(), erro));
            sessao.close();

            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Tuple> getBeneficios() {
        return beneficios;
    }

    public List<Tuple> getServicos() {
        return servicos;
    }
}
