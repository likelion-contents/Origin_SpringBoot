package dev.aquashdw.client.service;

import dev.aquashdw.client.model.NcpGeolocationDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class NcpApiService {
    private static final Logger logger = LoggerFactory.getLogger(NcpApiService.class);

    @Value("${ncp.api.access-key:stub-api-key}")
    private String accessKey;

    @Value("${ncp.api.secret-key:stub-secret-key}")
    private String secretKey;

    private static final String ncpHeaderNameTimestamp = "x-ncp-apigw-timestamp";
    private static final String ncpHeaderNameSignature = "x-ncp-apigw-signature-v2";

    private final WebClient ncpWebClient;

    public NcpApiService(WebClient ncpWebClient) {
        this.ncpWebClient = ncpWebClient;
    }

    public NcpGeolocationDto geoLocation(String ip){
        String epochString = String.valueOf(System.currentTimeMillis());
        String uriBase = "https://geolocation.apigw.ntruss.com";
        String uriPath = String.format("/geolocation/v2/geoLocation" +
                "?ip=%s&ext=t&responseFormatType=json", ip);

        String ncpSignature = this.makeSignature("GET", uriPath, epochString);

        return this.ncpWebClient
                .get()
                .uri(uriBase + uriPath)
//                .header(ncpHeaderNameTimestamp, epochString)
//                .header(ncpHeaderNameSignature, ncpSignature)
                .headers(httpHeaders -> {
                    httpHeaders.add(ncpHeaderNameTimestamp, epochString);
                    httpHeaders.add(ncpHeaderNameSignature, ncpSignature);
                })
                .exchangeToMono(clientResponse -> {
                    logger.trace(clientResponse.headers().toString());
                    return clientResponse.bodyToMono(NcpGeolocationDto.class);
                })
                .block();
    }

    private String makeSignature(String method, String path, String epochString) {
        String space = " ";					// one space
        String newLine = "\n";					// new line

        String message = method +
                space +
                path +
                newLine +
                epochString +
                newLine +
                accessKey;

        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
