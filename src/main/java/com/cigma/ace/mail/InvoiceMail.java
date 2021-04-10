package com.cigma.ace.mail;

import org.springframework.stereotype.Component;

@Component
public class InvoiceMail {

    public static String build() {
        return  "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h3>We thank you for you purchase!</h3>\n" +
                "    </body>\n" +
                "</html>\n";
    }
}
