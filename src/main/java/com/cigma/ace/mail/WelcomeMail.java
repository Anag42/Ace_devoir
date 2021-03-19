package com.cigma.ace.mail;

import com.cigma.ace.model.User;
import org.springframework.stereotype.Component;

@Component
public class WelcomeMail {

    public String build(User user, String password) {
        return  "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h3>Your credentials</h3>\n" +
                "        </br>\n" +
                "        <p>Username : " + user.getUsername() + " </p>\n" +
                "        <p>Password : " + password + " </p>\n" +
                "    </body>\n" +
                "</html>\n";
    }
}
