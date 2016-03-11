import static org.grails.plugins.wslite.WsliteEnhancer.enhance

class WsliteGrailsPlugin {

    def version = "0.7.2.0"
    def grailsVersion = "1.2.2 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "web-app/css",
            "web-app/images",
            "web-app/js/prototype",
            "web-app/js/application.js"
    ]
    def observe = ["controllers", "services"]

    def title = "Wslite plugin"
    def author = "Mihai Cazacu"
    def authorEmail = "cazacugmihai@gmail.com"
    def description = '''This plugin brings the power of https://github.com/jwagenleitner/groovy-wslite library to your
Grails app. Thanks to Andres Almiray to provided code at https://github.com/griffon/griffon-wslite-plugin!'''

    def documentation = "http://grails.org/plugin/wslite"

    def license = "APACHE"
    def organization = [name: "Macrobit Software", url: "http://macrobit.ro/"]
    def developers = [[name: "Mihai Cazacu", email: "cazacugmihai@gmail.com"]]
    def issueManagement = [system: "JIRA", url: "http://jira.grails.org/browse/GPWSLITE"]
    def scm = [url: 'https://github.com/grails-plugins/grails-wslite/']

    def doWithDynamicMethods = { ctx ->
        application.controllerClasses.each {c ->
            enhance c.metaClass
        }

        application.serviceClasses.each { s ->
            enhance s.metaClass
        }
    }

    def onChange = { event ->
        if (event.source) {
            if (application.isControllerClass(event.source)) {
                enhance application.getControllerClass(event.source.name).metaClass
            }

            if (application.isServiceClass(event.source)) {
                enhance application.getServiceClass(event.source.name).metaClass
            }
        }
    }

}