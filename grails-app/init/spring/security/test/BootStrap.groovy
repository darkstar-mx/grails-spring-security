package spring.security.test

import com.myapp.ProductAnnouncement
import com.myapp.Role
import com.myapp.User
import com.myapp.UserRole
import com.myapp.UserViewAccess
import com.myapp.ViewAccess
import com.myapp.ViewAccessRole

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        new ProductAnnouncement(message: 'Launch day').save()
        //new ProductAnnouncement(message: 'We keep adding features').save()

        ViewAccess viewAccess = new ViewAccess(name:'primary_view').save()

        Role userRoleApp = new Role(authority:'ROLE_ADMIN').save()

        User me = new User(username:'admin', password:'admin').save()

        UserRole.create(me,userRoleApp)

        UserViewAccess.create(me,viewAccess)

        ViewAccessRole.create(viewAccess,userRoleApp)

        UserRole.withSession {
            it.flush()
            it.clear()
        }
    }
    def destroy = {
    }
}
