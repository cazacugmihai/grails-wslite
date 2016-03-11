package org.grails.plugins.wslite

interface WsliteProvider {

    Object withHttp(Map params, Closure closure)
    Object withRest(Map params, Closure closure)
    Object withSoap(Map params, Closure closure)

}
