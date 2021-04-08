package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.*;
import org.gestorpublico.entidade.Bairro;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("default")
public class GetBeneficiosServicosPorPessoaAction extends PadraoAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa pessoa;
    private List<Map<String, Object>> beneficios = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> servicos = new ArrayList<Map<String, Object>>();

    @Action(value="getBeneficiosServicosPorPessoa",
        results={
            @Result(name="ok", type = "json", params = {"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            List<Tuple> benes = new Pessoa_BeneficioDAO(session).listePorPessoa(pessoa);
            Map<String, Object> map;
            for (Tuple tuple : benes) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("data", tuple.get("data"));
                map.put("nome", tuple.get("beneficio"));
                map.put("obse", tuple.get("observacao"));

                beneficios.add(map);
            }

            List<Tuple> servs = new Pessoa_ServicoDAO(session).listePorPessoa(pessoa);
            for (Tuple tuple : servs) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("data", tuple.get("data"));
                map.put("nome", tuple.get("servico"));
                map.put("obse", tuple.get("observacao"));

                servicos.add(map);
            }

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
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Map<String, Object>> getBeneficios() {
        return beneficios;
    }

    public List<Map<String, Object>> getServicos() {
        return servicos;
    }
}
