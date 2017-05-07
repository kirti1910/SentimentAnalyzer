package sentimentanalyzer

import grails.converters.JSON
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicNameValuePair;

public class GrailsUtils {

    static BasicHeader[] toHeaders(Map headers) {
        headers.collect { String k, String v -> new BasicHeader(k, v)}
    }

    static List<NameValuePair> toNameValuePair(Map params) {
        params.collect { k, v -> new BasicNameValuePair(k, v) }
    }

    static Map doGet(String url, Map headers) {
        doRequest(
                Request.Get(url),
                headers
        )
    }

    static Map doPost(String url, Map params, Map headers) {
        doRequest(
                Request.Post(url).bodyForm(toNameValuePair(params)),
                headers
        )
    }

    static Map doRequest(Request request, Map headers) {
        String responseStr = request.setHeaders(toHeaders(headers))
                .execute()
                .returnContent()
                .asString()

        JSON.parse(responseStr) as Map
    }

}
