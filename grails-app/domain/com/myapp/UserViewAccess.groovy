package com.myapp

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/7/2017
 *
 */
@ToString(cache = true, includeNames = true, includePackage = false)
class UserViewAccess implements Serializable {

    private static final long serialVersionUID = 1

    User userApp
    ViewAccess viewAccess

    @Override
    boolean equals(other) {
        if (other instanceof UserViewAccess) {
            other.userAppId == userApp?.id && other.viewAccessId == viewAccess?.id
        }
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (userApp) builder.append(userApp.id)
        if (viewAccess) builder.append(viewAccess.id)
        builder.toHashCode()
    }

    static UserViewAccess get(long userAppId, long viewAccessId) {
        criteriaFor(userAppId, viewAccessId).get()
    }

    static boolean exists(long userAppId, long viewAccessId) {
        criteriaFor(userAppId, viewAccessId).count()
    }

    private static DetachedCriteria criteriaFor(long userAppId, long viewAccessId) {
        UserViewAccess.where {
            userApp == User.load(userAppId) &&
                    viewAccess == ViewAccess.load(viewAccessId)
        }
    }

    static UserViewAccess create(User userApp, ViewAccess viewAccess) {
        def instance = new UserViewAccess(userApp: userApp, viewAccess: viewAccess)
        instance.save()
        instance
    }

    static boolean remove(User u, ViewAccess rg) {
        if (u != null && rg != null) {
            UserViewAccess.where { userApp == u && viewAccess == rg }.deleteAll()
        }
    }

    static int removeAll(User u) {
        u == null ? 0 : UserViewAccess.where { userApp == u }.deleteAll()
    }

    static int removeAll(ViewAccess rg) {
        rg == null ? 0 : UserViewAccess.where { viewAccess == rg }.deleteAll()
    }

    static constraints = {
        userApp validator: { User u, UserViewAccess ug ->
            if (ug.viewAccess?.id) {
                UserViewAccess.withNewSession {
                    if (UserViewAccess.exists(u.id, ug.viewAccess.id)) {
                        return ['userGroup.exists']
                    }
                }
            }
        }
    }

    static mapping = {
        id composite: ['viewAccess', 'userApp']
        version false
    }
}
