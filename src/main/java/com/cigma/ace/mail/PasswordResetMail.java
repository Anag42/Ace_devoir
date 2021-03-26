package com.cigma.ace.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.cigma.ace.model.PasswordReset;

@Component
public class PasswordResetMail {
	@Value("${app.fo.url}")
	private String FO_URL;
	    
	public String build(PasswordReset passwordReset) {
        return  "<!doctype html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "    <head>\n" +
                "        <meta charset=\"utf-8\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h3>Hello, Here is a link to reset your password : </h3>\n" +
                "        </br>\n" +
                "        <p> " + FO_URL + "?token=" + passwordReset.getToken() + " </p>\n" +
                "    </body>\n" +
                "</html>\n";
    }
}
