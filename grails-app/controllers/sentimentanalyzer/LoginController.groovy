package sentimentanalyzer

import grails.core.GrailsApplication

class LoginController {

    GrailsApplication grailsApplication

    def index() { }

    def auth() {

        Map requestMap =[
                "client_id" : "${grailsApplication.config.getProperty('web.client_id')}",
                "baseUrl" : "${grailsApplication.config.getProperty('authorization.baseUrl')}",
                "redirect_uri" : "${grailsApplication.config.getProperty('authorization.redirect_uri')}",
                "scope" : "${grailsApplication.config.getProperty('authorization.scope')}",
                "access_type" : "offline",
                "include_granted_scopes" : "true",
                "state" : "user",
                "response_type" : "code",
        ]

        render (view: 'auth', model: [requestMap:requestMap])
    }
}
