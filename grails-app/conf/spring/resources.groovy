import com.myapp.AuthManagerService
import com.myapp.CustomAuthenticationProvider

// Place your Spring DSL code here
beans = {

    //userDetailsService(AuthManagerService)


    customAuthenticationProvider(CustomAuthenticationProvider) {
        // attributes
    }
}
