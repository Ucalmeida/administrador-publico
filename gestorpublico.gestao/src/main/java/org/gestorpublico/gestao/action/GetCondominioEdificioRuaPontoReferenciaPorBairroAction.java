package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.gestorpublico.dao.*;
import org.gestorpublico.entidade.*;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;
import org.json.JSONObject;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("default")
public class GetCondominioEdificioRuaPontoReferenciaPorBairroAction extends PadraoAction {

    private Bairro bairro;
    private List<Map<String, Object>> ruas = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> condominios = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> edificios = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> pontosReferencia = new ArrayList<Map<String, Object>>();

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    @Action(value="getRuasCondominiosEdificiosReferenciasPorBairro",
        results={
            @Result(name="ok", type = "json", params = {"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            List<Tuple> rs = new RuaDAO(session).listePorBairro(bairro);
            Map<String, Object> map;
            for (Tuple tuple : rs) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("nome", tuple.get("tipo") + " " + tuple.get("nome"));

                ruas.add(map);
            }

            List<Tuple> conds = new CondominioDAO(session).listePorBairro(bairro);
            for (Tuple tuple : conds) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("nome", tuple.get("nome"));

                condominios.add(map);
            }

            List<Tuple> edifs = new EdificioDAO(session).listePorBairro(bairro);
            for (Tuple tuple : edifs) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("nome", tuple.get("nome"));

                edificios.add(map);
            }

            List<Tuple> refes = new Ponto_ReferenciaDAO(session).listePorBairro(bairro);
            for (Tuple tuple : refes) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("nome", tuple.get("nome"));

                pontosReferencia.add(map);
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
    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public List<Map<String, Object>> getRuas() {
        return ruas;
    }

    public List<Map<String, Object>> getCondominios() {
        return condominios;
    }

    public List<Map<String, Object>> getEdificios() {
        return edificios;
    }

    public List<Map<String, Object>> getPontosReferencia() {
        return pontosReferencia;
    }
}
