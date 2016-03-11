grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

    inherits "global"
    log "warn"

    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo 'https://oss.sonatype.org/content/groups/public'

        grailsRepo "http://grails.org/plugins"
    }

    dependencies {
        runtime 'com.github.groovy-wslite:groovy-wslite:0.7.2'
    }

    plugins {
        build(':release:2.2.1') {
            export = false
        }
    }

}

