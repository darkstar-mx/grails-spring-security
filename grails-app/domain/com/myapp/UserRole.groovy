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
class UserRole implements Serializable {

    private static final long serialVersionUID = 1

    User userApp
    Role roleApp

    @Override
    boolean equals(other) {
        if (other instanceof UserRole) {
            other.userAppId == userApp?.id && other.roleAppId == roleApp?.id
        }
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (userApp) builder.append(userApp.id)
        if (roleApp) builder.append(roleApp.id)
        builder.toHashCode()
    }

    static UserRole get(long userAppId, long roleAppId) {
        criteriaFor(userAppId, roleAppId).get()
    }

    static boolean exists(long userAppId, long roleAppId) {
        criteriaFor(userAppId, roleAppId).count()
    }

    private static DetachedCriteria criteriaFor(long userAppId, long roleAppId) {
        UserRole.where {
            userApp == User.load(userAppId) &&
                    roleApp == Role.load(roleAppId)
        }
    }

    static UserRole create(User userApp, Role roleApp) {
        def instance = new UserRole(userApp: userApp, roleApp: roleApp)
        instance.save()
        instance
    }

    static boolean remove(User u, Role r) {
        if (u != null && r != null) {
            UserRole.where { userApp == u && roleApp == r }.deleteAll()
        }
    }

    static int removeAll(User u) {
        u == null ? 0 : UserRole.where { userApp == u }.deleteAll()
    }

    static int removeAll(Role r) {
        r == null ? 0 : UserRole.where { roleApp == r }.deleteAll()
    }

    static constraints = {
        roleApp validator: { Role r, UserRole ur ->
            if (ur.userApp?.id) {
                UserRole.withNewSession {
                    if (UserRole.exists(ur.userApp.id, r.id)) {
                        return ['userRole.exists']
                    }
                }
            }
        }
    }

    static mapping = {
        id composite: ['userApp', 'roleApp']
        version false
    }
}
