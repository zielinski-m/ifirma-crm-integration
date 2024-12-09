package com.zss.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zss.client.IFirmaApiClient.checkApiLimit;
import static com.zss.client.IFirmaApiClient.fetchPaidInvoices;
import com.zss.config.UserAuth;

@Getter
@RestController
@RequestMapping("/api")
public class IFirmaController {

   private static String userName;
   private static String keyNameFaktura;
   private static String keyFaktura;
   private static String keyAbonent;
   private static String keyAbonentSpj;
   private static String keyFakturaSpj;
   private static String usernamespj;

    public IFirmaController(UserAuth userAuth) {

        userName = userAuth.getUsername();
        usernamespj = userAuth.getUsernamespj();
        keyNameFaktura = userAuth.getKeyNameFaktura();
        keyFaktura = userAuth.getKeyFaktura();
        keyAbonent = userAuth.getKeyAbonent();
        keyAbonentSpj = userAuth.getKeyAbonentSpj();
        keyFakturaSpj = userAuth.getKeyFakturaSpj();
    }

    @GetMapping("/invoices")
    public String getInvoices() {

        String apiUrlPaid = "https://www.ifirma.pl/iapi/faktury.json?" +
                "dataOd=2024-07-01&dataDo=2024-12-31&iloscNaStronie=300&status=oplacone";
        return fetchPaidInvoices(userName, keyNameFaktura, keyFaktura, apiUrlPaid) + fetchPaidInvoices(usernamespj, keyNameFaktura, keyFakturaSpj, apiUrlPaid);
    }
    @GetMapping("/limit")
    public String getApiLimit() {
        return checkApiLimit(userName, keyAbonent) + checkApiLimit(usernamespj, keyAbonentSpj);
    }
}
