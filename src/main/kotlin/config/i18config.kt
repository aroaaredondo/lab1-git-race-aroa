package es.unizar.webeng.hello.config
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*


/**
 * Configuration class to set up internationalization (i18n) support.
 */
@Configuration
class I18nConfig : WebMvcConfigurer {

    /**
     * Bean that manages the application's internationalized messages.
     *
     * @return a [MessageSource] that loads `messages.properties` files
     *         and supports UTF-8 encoding.
     */
    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages")  
        messageSource.setDefaultEncoding("UTF-8")                         
        return messageSource
    }

    /**
     * Bean that resolves the user's locale.
     *
     * Uses cookies to remember the user's language preference.
     * Defaults to [Locale.ENGLISH].
     *
     * @return a [LocaleResolver] that uses cookies.
     */
    @Bean
    fun localeResolver(): LocaleResolver {
        val resolver = CookieLocaleResolver()
        resolver.setDefaultLocale(Locale.ENGLISH)
        return resolver
    }

    
    /**
     * Bean that allows changing the language via a URL parameter.
     *
     * By default, the parameter name is `"lang"`. For example:
     * `/path?lang=es` will switch the locale to Spanish.
     *
     * @return a [LocaleChangeInterceptor] that intercepts requests.
     */
    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val interceptor = LocaleChangeInterceptor()
        interceptor.paramName = "lang" 
        return interceptor
    }

    /**
     * Registers the language change interceptor in the application.
     *
     * This allows Spring to detect the `"lang"` parameter in requests
     * and dynamically change the locale.
     *
     * @param registry the Spring MVC interceptor registry.
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }
}
