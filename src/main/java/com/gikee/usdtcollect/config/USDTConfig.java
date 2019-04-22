package com.gikee.usdtcollect.config;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import foundation.omni.rpc.OmniCLIClient;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lucas
 * @Date 2019.03.26
 */
@Configuration
public class USDTConfig {

    /**
     * 选择主网
     */
    private static final NetworkParameters netParams = MainNetParams.get();

    @Value("${usdt.username}")
    private String username;

    @Value("${usdt.password}")
    private String password;

    @Value("${usdt.clientAddress}")
    private String clientAddress;

    @Bean
    public JsonRpcHttpClient client() throws Throwable {
        String cred = Base64.encodeBase64String((username + ":" + password).getBytes());
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Authorization", "Basic " + cred);
        try {
            return new JsonRpcHttpClient(new URL(clientAddress), headers);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new JsonRpcHttpClient(new URL(clientAddress), headers);
    }

    @Bean
    public OmniCLIClient omniCLIClient() throws URISyntaxException {
        Context.propagate(new Context(netParams));
        return new OmniCLIClient(netParams, new URI(clientAddress), username, password);
    }

}
