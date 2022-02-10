package com.example.awss3;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
@Configuration
public class AmazonRequestSignatureV4Utils {
    public static String calculateAuthorizationHeaders(String method, String path, String query,
                                                       Map<String, String> headers, String bodySha256, String isoDateTime, String awsAccessKey, String awsSecret,
                                                       String awsRegion, String awsService) {
        try {

            String isoJustDate = isoDateTime.substring(0, 8);

            List<String> canonicalRequestLines = new ArrayList<>();
            canonicalRequestLines.add(method);
            canonicalRequestLines.add(path);
            canonicalRequestLines.add(query);
            List<String> hashedHeaders = new ArrayList<>();
            for (Entry<String, String> e : headers.entrySet()) {
                hashedHeaders.add(e.getKey().toLowerCase());
                canonicalRequestLines.add(e.getKey().toLowerCase() + ":" + normalizeSpaces(e.getValue().toString()));
            }
            canonicalRequestLines.add(null); // new line required after headers
            String signedHeaders = hashedHeaders.stream().collect(Collectors.joining(";"));
            canonicalRequestLines.add(signedHeaders);
            canonicalRequestLines.add(bodySha256);
            String canonicalRequestBody = canonicalRequestLines.stream().map(line -> line == null ? "" : line)
                    .collect(Collectors.joining("\n"));
            String canonicalRequestHash = hex(sha256(canonicalRequestBody.getBytes(StandardCharsets.UTF_8)));

            // (2)
            // https://docs.aws.amazon.com/general/latest/gr/sigv4-create-string-to-sign.html
            List<String> stringToSignLines = new ArrayList<>();
            stringToSignLines.add("AWS4-HMAC-SHA256");
            stringToSignLines.add(isoDateTime);
            String credentialScope = isoJustDate + "/" + awsRegion + "/" + awsService + "/aws4_request";
            stringToSignLines.add(credentialScope);
            stringToSignLines.add(canonicalRequestHash);
            String stringToSign = stringToSignLines.stream().collect(Collectors.joining("\n"));

            // (3)
            // https://docs.aws.amazon.com/general/latest/gr/sigv4-calculate-signature.html
            byte[] kDate = hmac(("AWS4" + awsSecret).getBytes(StandardCharsets.UTF_8), isoJustDate);
            byte[] kRegion = hmac(kDate, awsRegion);
            byte[] kService = hmac(kRegion, awsService);
            byte[] kSigning = hmac(kService, "aws4_request");
            String signature = hex(hmac(kSigning, stringToSign));

            System.out.println("signedHeaders--->"+signedHeaders);

            String authParameter = "AWS4-HMAC-SHA256 Credential=" + awsAccessKey + "/" + credentialScope
                    + ", SignedHeaders=" + signedHeaders + ", Signature=" + signature;
            // headers.put("Authorization", authParameter);
            System.out.println("Authorization--->"+authParameter);
            return authParameter;
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new IllegalStateException(e);
            }
        }
    }

    private static String normalizeSpaces(String value) {
        return value.replaceAll("\\s+", " ").trim();
    }

    public static String hex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] sha256(byte[] bytes) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(bytes);
        return digest.digest();
    }

    public static byte[] hmac(byte[] key, String msg) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }

}
