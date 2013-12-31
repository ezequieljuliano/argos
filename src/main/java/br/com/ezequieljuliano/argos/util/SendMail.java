/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SendMail {

    private Server server;
    private Involved sender;
    private List<Involved> recipients = new ArrayList<Involved>();
    private String subject;
    private String message;
    private List<Attachment> attachment = new ArrayList<Attachment>();

    public SendMail() {
    }

    public SendMail using(Server server) {
        this.server = server;
        return this;
    }

    public SendMail from(String email, String name) {
        this.sender = new Involved(email, name);
        return this;
    }

    public SendMail from(String email) {
        this.sender = new Involved(email, "");
        return this;
    }

    public SendMail to(String email, String name) {
        this.recipients.add(new Involved(email, name));
        return this;
    }

    public SendMail to(String email) {
        this.recipients.add(new Involved(email, ""));
        return this;
    }

    public SendMail subject(String subject) {
        this.subject = subject;
        return this;
    }

    public SendMail message(String message) {
        this.message = message;
        return this;
    }

    public SendMail attaching(Attachment annex) throws FileNotFoundException {
        File fileAnnex = new File(annex.getPath());
        if (!fileAnnex.exists()) {
            throw new FileNotFoundException("File (" + fileAnnex.getPath() + ") does not exist!");
        }
        this.attachment.add(annex);
        return this;
    }

    public void send() throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(server.getHostName());
        email.setAuthentication(server.getUser(), server.getPassWord());
        email.setSSL(server.isSSL());
        email.setSmtpPort(server.getPort());

        for (Involved involved : recipients) {
            email.addTo(involved.getEmail(), involved.getName());
        }

        email.setFrom(sender.getEmail(), sender.getName());
        email.setSubject(subject);
        email.setHtmlMsg(message);
        email.setCharset("UTF-8");

        EmailAttachment att;
        for (Attachment annex : attachment) {
            att = new EmailAttachment();
            att.setPath(annex.getPath());
            att.setDisposition(EmailAttachment.ATTACHMENT);
            att.setDescription(annex.getDescription());
            att.setName(annex.getName());
            email.attach(att);
        }

        email.send();
    }

    public static class Attachment {

        private String name;
        private String description;
        private String path;

        public Attachment(String name, String description, String path) {
            this.name = name;
            this.description = description;
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPath() {
            return path;
        }

    }

    private class Involved {

        private String email;
        private String name;

        public Involved(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

    }

    public static class Server {

        private String hostName;
        private Integer port;
        private String user;
        private String passWord;
        private boolean SSL = false;

        public Server() {
        }

        public Server hostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public Server port(Integer port) {
            this.port = port;
            return this;
        }

        public Server user(String user) {
            this.user = user;
            return this;
        }

        public Server passWord(String passWord) {
            this.passWord = passWord;
            return this;
        }

        public Server withSSL(boolean SSL) {
            this.SSL = SSL;
            return this;
        }

        public String getHostName() {
            return hostName;
        }

        public Integer getPort() {
            return port;
        }

        public String getUser() {
            return user;
        }

        public String getPassWord() {
            return passWord;
        }

        public boolean isSSL() {
            return SSL;
        }

    }

}
