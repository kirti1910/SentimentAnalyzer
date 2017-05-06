package sentimentanalyzer

import groovy.time.TimeCategory

import java.util.concurrent.ConcurrentHashMap

class HomeController {

    Map accessResponse

    static Map<String, String> messages = new ConcurrentHashMap<>()

    def index() {
        def access_token = params.access_token
        if (!access_token) {
            def config = grailsApplication.config
            String baseUrl = config.getProperty('authenticate.baseUrl')
            String code = params.code
            Map<String, String> requestMap =[
                    "client_id" : "${config.getProperty('web.client_id')}".toString(),
                    "client_secret" : "${config.getProperty('web.client_secret')}".toString(),
                    "redirect_uri" : "${config.getProperty('authorization.redirect_uri')}".toString(),
                    "grant_type" : "${config.getProperty('authenticate.grant_type')}".toString(),
                    'code': code
            ]
            Map<String, String> headers1 =["content-type":"application/x-www-form-urlencoded"]

            accessResponse = GrailsUtils.doPost(baseUrl, requestMap, headers1)
            int expires_in = accessResponse.expires_in
            Date date = new Date()
            use(TimeCategory) {
                date += expires_in.seconds - 1.minute
            }
            accessResponse.expires_date = date      //TODO Do something with expire
            access_token = accessResponse.access_token

            Map headers2 = [
                    'content-Type': 'application/json',
                    'authorization': "Bearer $access_token".toString()
            ]

            def response2 = GrailsUtils.doPost("https://www.googleapis.com/gmail/v1/users/me/messages", [:], headers2)
                                                  //TODO Make less messages
            response2.messages.id.each { messageId ->
                if (!messages.containsKey(messageId)) {
                    String snippet = GrailsUtils.doGet(
                            "https://www.googleapis.com/gmail/v1/users/me/messages/$messageId?format=full",
                            headers2
                    ).snippet
                    messages.put(messageId, snippet)
                }

            }

        }
    }
}
