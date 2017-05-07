package sentimentanalyzer

import grails.converters.JSON
import groovy.time.TimeCategory
import sentimentanalyzer.utils.CommonUtils

import java.util.concurrent.ConcurrentHashMap

class HomeController {

    Map accessResponse

    static Map<String, Object> messages = new ConcurrentHashMap<>()
    static Map<String, Map> messageSentiments = new ConcurrentHashMap<>()

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

            def response2 = GrailsUtils.doGet("https://www.googleapis.com/gmail/v1/users/me/messages?maxResults=10", headers2)
            response2.messages.id.findAll { messageId ->
                !messages.containsKey(messageId)
            }.each { messageId ->
                def email_content = GrailsUtils.doGet(
                        "https://www.googleapis.com/gmail/v1/users/me/messages/$messageId?format=full",
                        headers2
                )
                messages.put(messageId, email_content)
            }

        }

        redirect(action: 'report')

    }

    def report(){

        messages.findAll { k, v ->
            ! messageSentiments.containsKey(k)
        }.each { k,v ->
            String messageId = k
            String snippet = v.snippet
            def command = "/home/ttnd/anaconda3/bin/python /home/ttnd/nbs/sentiment.py ${snippet}"

            def sout = new StringBuilder()

            def process = command.execute()
            process.consumeProcessOutput(sout, System.err)
            process.waitForOrKill(1000)

            messageSentiments.put(messageId, JSON.parse(sout.toString()))
        }

        Map msgList = messages
                .groupBy { k, v ->
            v.payload.headers.findAll { it.name == 'From'}.value

        }.collectEntries { k, v ->

            String key = k[0]
            def value = v.keySet().collect {
                messageSentiments.get(it)
            }
            List subjectivities = value*.subjectivity
            List polarities = value*.polarity
            Double subjectivity = CommonUtils.average(subjectivities)
            Double polarity = CommonUtils.average(polarities)
            [(key): [
                    'subjectivity': subjectivity,
                    'polarity': polarity,
                    'emails': subjectivities.size(),
                    'score': CommonUtils.getScore(polarity)
            ]]
        }
        render(view: 'report', model: [messages: msgList , overallScore: CommonUtils.getOverallScore(msgList)])
    }
}
