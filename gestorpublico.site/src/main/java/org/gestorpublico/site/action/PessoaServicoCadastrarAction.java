package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.dao.ServicoDAO;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.entidade.Servico;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import java.util.List;

@ParentPackage("default")
public class PessoaServicoCadastrarAction extends PadraoAction {

    private Servico servico;
    private String observacao;

    @Action(value = "pessoaServicoCadastrar",
        results = {
            @Result(name = "ok", type = "httpheader", params = {"status", "200"}),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            servico = new ServicoDAO(session).getServico(servico);

            if (servico == null) {
                response.setHeader("erro", "Serviço não encontrado.");
                return "erro";
            }

            if (!servico.isAtivo()) {
                response.setHeader("erro", "Esse Serviço não está disponível no momento.");
                return "erro";
            }

            Pessoa_Servico pessoaServico = new Pessoa_Servico();
            pessoaServico.setServico(servico);
            pessoaServico.setSolicitante(getPessoaLogada());
            pessoaServico.setObservacao(observacao);
            new Pessoa_ServicoDAO(session).salvar(pessoaServico);

            response.setHeader("id", pessoaServico.getId().toString());
            response.setHeader("msg", "Serviço solicitado com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao == null || observacao.trim().isEmpty() ? null : observacao.trim();
    }
}
