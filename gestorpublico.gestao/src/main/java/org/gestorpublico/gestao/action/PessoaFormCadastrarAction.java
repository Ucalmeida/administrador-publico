package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.*;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Municipio;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ParentPackage("default")
public class PessoaFormCadastrarAction extends PadraoAction {

    private List<Tuple> ufs;
    private List<Tuple> municipios;
    private List<Tuple> bairros;
    private List<Tuple> sexos;
    private Municipio municipio;

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    @Action(value="cidadao",
        results={
            @Result(name="ok", location="formNovoPessoa.jsp"),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            MunicipioDAO municipioDAO = new MunicipioDAO(session);
            municipio = municipioDAO.getMunicipioPorNomeUFNomeMunicipio("Sergipe", "Riachuelo");

            ufs = new UFDAO(session).listeUFPorNomePais("Brasil");
            municipios = municipioDAO.listeMunicipioPorUF(municipio.getUf());
            bairros = new BairroDAO(session).listeBairroPorMunicipio(municipio);
            sexos = new SexoDAO(session).listeSexos();

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
    public List<Tuple> getUfs() {
        return ufs;
    }

    public List<Tuple> getMunicipios() {
        return municipios;
    }

    public List<Tuple> getBairros() {
        return bairros;
    }

    public List<Tuple> getSexos() {
        return sexos;
    }

    public Municipio getMunicipio() {
        return municipio;
    }
}
