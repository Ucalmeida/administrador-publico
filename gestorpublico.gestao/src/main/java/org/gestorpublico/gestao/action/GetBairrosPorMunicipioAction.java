package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.BairroDAO;
import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.MunicipioDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Municipio;
import org.gestorpublico.entidade.UF;
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
public class GetBairrosPorMunicipioAction extends PadraoAction {

    private Municipio municipio;
    private List<Map<String, Object>> objetos = new ArrayList<Map<String, Object>>();

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    @Action(value="getBairrosPorMunicipio",
        results={
            @Result(name="ok", type = "json", params = {"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            List<Tuple> rs = new BairroDAO(session).listeBairroPorMunicipio(municipio);
            Map<String, Object> map;
            for (Tuple tuple : rs) {
                map = new HashMap<>();
                map.put("id", tuple.get("id"));
                map.put("nome", tuple.get("nome"));

                objetos.add(map);
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
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Map<String, Object>> getObjetos() {
        return objetos;
    }
}
