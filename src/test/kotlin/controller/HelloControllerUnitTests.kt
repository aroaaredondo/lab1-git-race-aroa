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
        }
        controller = HelloController(messageSource)
        LocaleContextHolder.setLocale(Locale.ENGLISH)
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Test Message")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController(messageSource)
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).isEqualTo("Hello, Test!")
        assertThat(response["timestamp"]).isNotNull()
    }
}
