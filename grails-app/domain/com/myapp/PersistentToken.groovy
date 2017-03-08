package com.myapp

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/7/2017
 *
 */
@EqualsAndHashCode(includes = ['series', 'username'])
@ToString(includes = ['series', 'username'], cache = true, includeNames = true, includePackage = false)
class PersistentToken implements Serializable {

    private static final long serialVersionUID = 1

    String series
    String username
    String token
    Date lastUsed

    static constraints = {
        series maxSize: 64
        token maxSize: 64
        username maxSize: 64
    }

    static mapping = {
        table 'persistent_login'
        id name: 'series', generator: 'assigned'
        version false
    }
}
