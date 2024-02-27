package dev.luisoliveira.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class RefreshScopeController {

    @Value("${msuser.refreshscope.name}")
    private String name;

    @RequestMapping("/refreshscope")
    public String refreshscope(){
        return this.name;
    }
}
