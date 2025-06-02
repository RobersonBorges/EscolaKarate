package br.com.karate.escola.EscolaKarate.geral.email.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

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

    public void enviarEmailRedefinicaoSenha(String destinatario, String resetLink)
    {
        SimpleMailMessage messagem = new SimpleMailMessage();
        messagem.setTo(destinatario);
        messagem.setSubject( "Redefinição de Senha - Sistema Karate");
        messagem.setText("Olá, você solicitou a redefinição de senha.\n\n" +
                "Clique no link abaixo para criar uma nova senha:\n" +
                resetLink + "\n\n" +
                "Este link é válido por 1 hora. Se você não solicitou esta redefinição, ignore este email.\n\n" +
                "Atenciosamente,\nEquipe da Escola de Karate");

        mailSender.send(messagem);
    }

    public void enviarEmailNotificacaoMudancaSenha(String destinatario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Notificação de Mudança de Senha");
        message.setText("Olá,\n\n" +
                "Sua senha foi alterada com sucesso.\n\n" +
                "Se você não solicitou essa mudança, entre em contato com o suporte imediatamente.\n\n" +
                "Atenciosamente,\n" +
                "Equipe da Escola de Karatê");

        mailSender.send(message);
    }
}