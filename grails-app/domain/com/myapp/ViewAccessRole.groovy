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
class ViewAccessRole implements Serializable {

    private static final long serialVersionUID = 1

    ViewAccess viewAccess
    Role roleApp

    @Override
    boolean equals(other) {
        if (other instanceof ViewAccessRole) {
            other.roleAppId == roleApp?.id && other.viewAccessId == viewAccess?.id
        }
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (viewAccess) builder.append(viewAccess.id)
        if (roleApp) builder.append(roleApp.id)
        builder.toHashCode()
    }

    static ViewAccessRole get(long viewAccessId, long roleAppId) {
        criteriaFor(viewAccessId, roleAppId).get()
    }

    static boolean exists(long viewAccessId, long roleAppId) {
        criteriaFor(viewAccessId, roleAppId).count()
    }

    private static DetachedCriteria criteriaFor(long viewAccessId, long roleAppId) {
        ViewAccessRole.where {
            viewAccess == ViewAccess.load(viewAccessId) &&
                    roleApp == Role.load(roleAppId)
        }
    }

    static ViewAccessRole create(ViewAccess viewAccess, Role roleApp) {
        def instance = new ViewAccessRole(viewAccess: viewAccess, roleApp: roleApp)
        instance.save()
        instance
    }

    static boolean remove(ViewAccess rg, Role r) {
        if (rg != null && r != null) {
            ViewAccessRole.where { viewAccess == rg && roleApp == r }.deleteAll()
        }
    }

    static int removeAll(Role r) {
        r == null ? 0 : ViewAccessRole.where { roleApp == r }.deleteAll()
    }

    static int removeAll(ViewAccess rg) {
        rg == null ? 0 : ViewAccessRole.where { viewAccess == rg }.deleteAll()
    }

    static constraints = {
        roleApp validator: { Role r, ViewAccessRole rg ->
            if (rg.viewAccess?.id) {
                ViewAccessRole.withNewSession {
                    if (ViewAccessRole.exists(rg.viewAccess.id, r.id)) {
                        return ['roleGroup.exists']
                    }
                }
            }
        }
    }

    static mapping = {
        id composite: ['viewAccess', 'roleApp']
        version false
    }
}
