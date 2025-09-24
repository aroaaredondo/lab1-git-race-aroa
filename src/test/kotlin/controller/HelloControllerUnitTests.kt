package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import org.springframework.context.MessageSource
import org.springframework.context.support.StaticMessageSource
import java.util.Locale
import org.springframework.context.i18n.LocaleContextHolder

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    private lateinit var messageSource: MessageSource
    
    @BeforeEach
    fun setup() {
        
        model = ExtendedModelMap()
        messageSource = StaticMessageSource().apply {
            
            addMessage("app.message", Locale.ENGLISH, "Test Message")
            addMessage("greeting", Locale.ENGLISH, "Hello, {0}!")

            addMessage("app.message", Locale("es"), "Mensaje de Prueba")
            addMessage("greeting", Locale("es"), "Hola, {0}!")

            addMessage("app.message", Locale.FRENCH, "Message de Test")
            addMessage("greeting", Locale.FRENCH, "Bonjour, {0}!")

        }
        controller = HelloController(messageSource)
    }
    
    @Test
    fun `should return welcome view with default message in English`() {
        // Ensure locale is set to English
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Test Message")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message in English`() {
        // Ensure locale is set to English
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp in English`() {
        // Ensure locale is set to English
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val apiController = HelloApiController(messageSource)
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hello, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }
    

    @Test
    fun `should return welcome view with default message in Spanish`() {
        // Change locale to Spanish
        LocaleContextHolder.setLocale(Locale("es"))
        
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Mensaje de Prueba")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }

    @Test
    fun `should return welcome view with personalized message in Spanish`() {
        // Change locale to Spanish
        LocaleContextHolder.setLocale(Locale("es"))

        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hola, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }

    @Test
    fun `should return API response with timestamp in Spanish`() {
        // Change locale to Spanish
        LocaleContextHolder.setLocale(Locale("es"))

        val apiController = HelloApiController(messageSource)
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hola, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }

    @Test
    fun `should return welcome view with default message in French`() {
        // Change locale to French
        LocaleContextHolder.setLocale(Locale.FRENCH)
        
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Message de Test")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }

    @Test
    fun `should return welcome view with personalized message in French`() {
        // Change locale to French
        LocaleContextHolder.setLocale(Locale.FRENCH)

        val view = controller.welcome(model, "Developer")

        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Bonjour, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }

    @Test
    fun `should return API response with timestamp in French`() {
        // Change locale to French
        LocaleContextHolder.setLocale(Locale.FRENCH)

        val apiController = HelloApiController(messageSource)
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Bonjour, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }
}
