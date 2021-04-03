package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.BeneficioDAO;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.dao.ServicoDAO;
import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.entidade.Servico;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

@ParentPackage("default")
public class PessoaBeneficioCadastrarAction extends PadraoAction {

    private Beneficio beneficio;
    private String observacao;

    @Action(value = "pessoaBeneficioCadastrar",
        results = {
            @Result(name = "ok", type = "httpheader", params = {"status", "200"}),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            beneficio = new BeneficioDAO(session).getBeneficio(beneficio);

            if (beneficio == null) {
                response.setHeader("erro", "Benefício não encontrado.");
                return "erro";
            }

            if (!beneficio.isAtivo()) {
                response.setHeader("erro", "Esse Benefício não está disponível no momento.");
                return "erro";
            }

            Pessoa_BeneficioDAO pessoaBeneficioDAO = new Pessoa_BeneficioDAO(session);

            if (pessoaBeneficioDAO.jaExistePorBeneficioSolicitanteSemDespacho(beneficio, getPessoaLogada())) {
                response.setHeader("erro", "Você já solicitou esse benefício. Aguarde o despacho.");
                return "erro";
            }

            Pessoa_Beneficio pessoaBeneficio = new Pessoa_Beneficio();
            pessoaBeneficio.setBeneficio(beneficio);
            pessoaBeneficio.setBeneficiado(getPessoaLogada());
            pessoaBeneficio.setObservacao(observacao);
            new Pessoa_BeneficioDAO(session).salvar(pessoaBeneficio);

            response.setHeader("id", pessoaBeneficio.getId().toString());
            response.setHeader("msg", "Benefício solicitado com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}
