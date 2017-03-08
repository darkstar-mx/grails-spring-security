# grails-spring-security
Grails implementation of grails-spring-security-core plugin, it particularly focus on chapters:
* Authentication Providers (chapter 10, at the moment of writing)
* Custom UserDetailsService (chapter 11)

Plugin documentation is available on http://grails-plugins.github.io/grails-spring-security-core/v3/index.html
## Switching between _Authentication Providers_ and _Custom UserDetailsService_
* Open up file grails-app/conf/spring/resources.groovy
```groovy
// Place your Spring DSL code here
beans = {

    //userDetailsService(AuthManagerService)


    customAuthenticationProvider(CustomAuthenticationProvider) {
        // attributes
    }
}
```
* Enable only one of the configurations(userDetailsService or customAuthenticationProvider), ensure to comment/delete the remaining one.

## Executing project

Grails execution
```bash
cd grails-spring-security
grails run-app
```
Gradle execution
```bash
cd grails-spring-security
gradle bootRun
```
