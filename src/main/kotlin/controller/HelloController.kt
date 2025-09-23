package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

@Controller
class HelloController(
    /**
     * The message source for internationalization (i18n).
     */
    private val messageSource: MessageSource
) {
    
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val locale = LocaleContextHolder.getLocale()
        val greeting = if (name.isNotBlank()){
            messageSource.getMessage("greeting", arrayOf(name), locale)
        } else {
            messageSource.getMessage("app.message", null, locale)
        }
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController ( private val messageSource: MessageSource) {
    
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val locale = LocaleContextHolder.getLocale()
        val message = messageSource.getMessage("greeting", arrayOf(name), locale)
        return mapOf(
            "message" to message,
            "timestamp" to java.time.Instant.now().toString()
        )
    }
}
