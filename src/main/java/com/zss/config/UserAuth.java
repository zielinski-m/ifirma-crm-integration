package com.zss.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class UserAuth {

    @Value("${ifirma.api.username}")
    String username;

    @Value("${ifirma.api.keyNameAbonent}")
    String keyNameAbonent;

    @Value("${ifirma.api.keyNameFaktura}")
    String keyNameFaktura;

    @Value("${ifirma.api.keyAbonent}")
    String keyAbonent;

    @Value("${ifirma.api.keyFaktura}")
    String keyFaktura;

    @Value("${ifirma.api.n8n}")
    String n8nWebhookUrl;

}
