package br.com.ezequieljuliano.argos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailSender {

    private Servidor servidor;
    private Email remetente;
    private List<Email> destinatarios = new ArrayList<Email>();
    private String assunto;
    private String mensagem;
    private List<Anexo> anexos = new ArrayList<Anexo>();

    public EmailSender() {
    }

    public EmailSender usando(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public EmailSender de(String email, String nome) {
        this.remetente = new Email(email, nome);
        return this;
    }

    public EmailSender de(String email) {
        this.remetente = new Email(email, "");
        return this;
    }

    public EmailSender para(String email, String nome) {
        this.destinatarios.add(new Email(email, nome));
        return this;
    }

    public EmailSender para(String email) {
        this.destinatarios.add(new Email(email, ""));
        return this;
    }

    public EmailSender comAssunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public EmailSender comMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public EmailSender anexando(Anexo anexo) throws FileNotFoundException {
        File arquivoAnexo = new File(anexo.caminho);
        if (!arquivoAnexo.exists()) {
            throw new FileNotFoundException("O arquivo (" + anexo.caminho + ") anexado não existe");
        }
        this.anexos.add(anexo);
        arquivoAnexo = null;
        return this;
    }

    public void enviar() throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(servidor.endereco);
        email.setAuthentication(servidor.usuario, servidor.senha);
        email.setSSL(servidor.ssl);

        //email.setSslSmtpPort(servidor.porta);
        email.setSmtpPort(servidor.porta);

        for (Email destinatario : destinatarios) {
            email.addTo(destinatario.getEmail(), destinatario.getNome());
        }

        email.setFrom(remetente.getEmail(), remetente.getNome());
        email.setSubject(assunto);
        email.setHtmlMsg(mensagem);
        email.setCharset("UTF-8");

        EmailAttachment attachment;
        for (Anexo anexo : anexos) {
            attachment = new EmailAttachment();
            attachment.setPath(anexo.getCaminho());
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(anexo.getDescricao());
            attachment.setName(anexo.getNome());
            email.attach(attachment);
        }

        email.send();
    }

    public static class Anexo {

        private String nome;
        private String descricao;
        private String caminho;

        public Anexo(String nome, String descricao, String caminho) {
            this.nome = nome;
            this.descricao = descricao;
            this.caminho = caminho;
        }

        public String getCaminho() {
            return caminho;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getNome() {
            return nome;
        }
    }

    private class Email {

        private String email;
        private String nome;

        public Email(String email, String nome) {
            this.email = email;
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class Servidor {

        private String endereco;
        private Integer porta;
        private String usuario;
        private String senha;
        private boolean ssl = false;

        public Servidor() {
        }

        public Servidor endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public Servidor porta(Integer porta) {
            this.porta = porta;
            return this;
        }

        public Servidor usuario(String usuario) {
            this.usuario = usuario;
            return this;
        }

        public Servidor senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Servidor usandoSsl() {
            this.ssl = true;
            return this;
        }

        public Servidor ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public String getEndereco() {
            return endereco;
        }

        public Integer getPorta() {
            return porta;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getSenha() {
            return senha;
        }

        public boolean isSsl() {
            return ssl;
        }
    }
}
