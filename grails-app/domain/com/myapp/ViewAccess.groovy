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
@EqualsAndHashCode(includes = 'name')
@ToString(includes = 'name', includeNames = true, includePackage = false)
class ViewAccess implements Serializable {

    private static final long serialVersionUID = 1

    String name

    Set<Role> getAuthorities() {
        ViewAccessRole.findAllByViewAccess(this)*.roleApp
    }

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        cache true
    }
}
