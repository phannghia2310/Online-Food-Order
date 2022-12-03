package com.example.backendjavaspring.service;

public interface MailService {
    boolean sendEmail(String email, String content);
}
