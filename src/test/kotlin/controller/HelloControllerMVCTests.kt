package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.BDDMockito.given
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq

@WebMvcTest(HelloController::class, HelloApiController::class)
class HelloControllerMVCTests {

    @MockBean
    private lateinit var messageSource: MessageSource

    @Value("\${app.message:Welcome to the Modern Web App!}")
    private lateinit var message: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return home page with default message in English`() {
        given(messageSource.getMessage(eq("app.message"), any<Array<Any>>(), any()))
            .willReturn("Test Message")

        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Test Message"))
            .andExpect(model().attribute("name", ""))
    }
    
    @Test
    fun `should return home page with default message in Spanish`() {
        given(messageSource.getMessage(eq("app.message"), any<Array<Any>>(), any()))
            .willReturn("Mensaje de Prueba")

        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Mensaje de Prueba"))
            .andExpect(model().attribute("name", ""))
    }

        @Test
    fun `should return home page with default message in French`() {
        given(messageSource.getMessage(eq("app.message"), any<Array<Any>>(), any()))
            .willReturn("Message de Test")

        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Message de Test"))
            .andExpect(model().attribute("name", ""))
    }


    @Test
    fun `should return home page with personalized message in English`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Hello, Developer!")

        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Hello, Developer!"))
            .andExpect(model().attribute("name", "Developer"))
    }

    @Test
    fun `should return home page with personalized message in Spanish`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Hola, Developer!")

        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Hola, Developer!"))
            .andExpect(model().attribute("name", "Developer"))
    }

        @Test
    fun `should return home page with personalized message in French`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Bonjour, Developer!")

        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", "Bonjour, Developer!"))
            .andExpect(model().attribute("name", "Developer"))
    }

    @Test
    fun `should return API response as JSON in English`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Hello, Test!")

        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", equalTo("Hello, Test!")))
            .andExpect(jsonPath("$.timestamp").exists())
    }

    @Test
    fun `should return API response as JSON in Spanish`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Hola, Test!")

        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", equalTo("Hola, Test!")))
            .andExpect(jsonPath("$.timestamp").exists())
    }

    fun `should return API response as JSON in French`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Bonjour, Test!")

        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", equalTo("Bonjour, Test!")))
            .andExpect(jsonPath("$.timestamp").exists())
    }

}
