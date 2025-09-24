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
    fun `should return home page with default message`() {
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
    fun `should return home page with personalized message`() {
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
    fun `should return API response as JSON`() {
        given(messageSource.getMessage(eq("greeting"), any<Array<Any>>(), any()))
            .willReturn("Hello, Test!")

        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", equalTo("Hello, Test!")))
            .andExpect(jsonPath("$.timestamp").exists())
    }
}
