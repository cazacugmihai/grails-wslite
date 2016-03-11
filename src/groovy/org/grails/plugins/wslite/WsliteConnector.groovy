package org.grails.plugins.wslite

import wslite.http.HTTPClient
import wslite.rest.RESTClient
import wslite.soap.SOAPClient

import java.util.concurrent.ConcurrentHashMap

@Singleton
class WsliteConnector implements WsliteProvider {

    final Map BUILDERS = new ConcurrentHashMap()
    static final HTTP_PROPERTIES = [
            'connectTimeout', 'readTimeout',
            'followRedirects', 'useCaches',
            'sslTrustAllCerts', 'sslTrustStoreFile',
            'sslTrustStorePassword', 'proxy',
            'httpConnectionFactory', 'authorization'
    ]

    Object withHttp(Map params, Closure closure) {
        return doWithClient(HTTPClient, params, closure)
    }

    Object withRest(Map params, Closure closure) {
        return doWithClient(RESTClient, params, closure)
    }

    Object withSoap(Map params, Closure closure) {
        return doWithClient(SOAPClient, params, closure)
    }

    // utils

    private Object doWithClient(Class clazz, Map params, Closure closure) {
        def client = configureClient(clazz, params)

        if (closure) {
            closure.delegate = client
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            return closure()
        }

        return null
    }

    private def configureClient(Class clazz, Map params) {
        def client = null

        if (params.id) {
            String id = params.remove('id').toString()
            client = BUILDERS[id]
            if (client == null) {
                client = makeClient(clazz, params)
                BUILDERS[id] = client
            }
        } else {
            client = makeClient(clazz, params)
        }

        client
    }

    private def makeClient(Class clazz, Map params) {
        Map httpParams = [:]
        HTTP_PROPERTIES.each { name ->
            def value = params.remove(name)
            if (value != null) httpParams[name] = value
        }

        def client = clazz.newInstance(params)

        if (!(client instanceof HTTPClient)) {
            httpParams.each { k, v ->
                client.httpClient[k] = v
            }
        }

        client
    }

}
