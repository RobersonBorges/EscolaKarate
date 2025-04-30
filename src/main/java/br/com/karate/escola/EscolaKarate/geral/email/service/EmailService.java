package br.com.karate.escola.EscolaKarate.geral.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailCadastro(String destinatario, String username, String senha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Bem-vindo à Escola de Karatê - Dados de Acesso");
        message.setText("Olá,\n\n" +
                "Seu cadastro na Escola de Karatê foi realizado com sucesso!\n\n" +
                "Aqui estão seus dados de acesso:\n" +
                "Usuário: " + username + "\n" +
                "Senha: " + senha + "\n\n" +
                "Recomendamos que você altere sua senha no primeiro acesso.\n\n" +
                "Atenciosamente,\n" +
                "Equipe da Escola de Karatê");

        mailSender.send(message);
    }
}