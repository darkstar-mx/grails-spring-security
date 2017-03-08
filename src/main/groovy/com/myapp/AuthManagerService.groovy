package com.myapp

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import grails.transaction.Transactional
import org.springframework.dao.DataAccessException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 *
 * @author ArmandodeJesus
 * @email aj.montoya@outlook.com
 * @Date 3/7/2017
 * @Copyright © Armando Montoya, 2015
 * All rights reserved
 *
 */
@Service
class AuthManagerService implements GrailsUserDetailsService{

      /**
       * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least
       * one role, so we give a user with no granted roles this one which gets
       * past that restriction but doesn't grant anything.
       */
    static final List NO_ROLES = [new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)]

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException, DataAccessException {
        println "loadUserByUsername:: two params"
        return loadUserByUsername(username)
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = [IllegalArgumentException,UsernameNotFoundException])
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        println "loadUserByUsername:: one param"
        User user = User.findByUsername(username)
        if (!user)
            throw new NoStackUsernameNotFoundException()

        println "loadUserByUsername::User contents-> ${user.dump()}"

        //Set<ViewAccess> roles = user.authorities

        Set<Role> roles = user.authorities.collect {it.authorities}.flatten().unique()

        def authorities = roles.collect(){
            new SimpleGrantedAuthority(it.authority)
        }

        return new AuthManagerBean(user.username,
                                    user.password,
                                    user.enabled,
                                    !user.accountExpired,
                                    !user.passwordExpired,
                                    !user.accountLocked,
                                    authorities ?: NO_ROLES, user.id,
                                    "Armando Montoya")

    }
}
