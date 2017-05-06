package sentimentanalyzer

import grails.converters.JSON;

public class GrailsUtils {
    static Map doGet(String url, Map headers) {
        def conn = HttpUtility.sendGetRequest(url, headers);
        String[] response = HttpUtility.readMultipleLinesRespone(conn);

        Map responseMap = JSON.parse(response.join("") ) as Map

        responseMap
    }

    static Map doPost(String url, Map params, Map headers) {

        println("url: $url, params:$params, headers:$headers")

        def conn = HttpUtility.sendPostRequest(url, params, headers);
        String[] response = HttpUtility.readMultipleLinesRespone(conn);

        Map responseMap = JSON.parse(response.join("") ) as Map

        println("responseMap:$responseMap")

        responseMap
    }

}
