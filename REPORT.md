# Lab 1 Git Race -- Project Report

**Aroa Redondo Zamora (851769)**

## Description of Changes

**Multi-language support (i18n)** was implemented using Spring Boot `MessageSource`. This feature was chosen because it was interesting and had not been developed in previous projects.

1. Added `.properties` files for each language: English, Spanish, and French.
2. Updated Thymeleaf templates to use i18n expressions instead of static texts.
3. Defined keys for interface texts (e.g., `title`, `section1.description`) with their translations.
4. Modified HTML views to include buttons that allow switching the page language.
5. Modified the controller to support the three languages.
6. Added tests to verify the interface in all supported languages.

## Technical Decisions

To implement multi-language support, a configuration class named `I18nConfig.kt` was created. This class customizes the Spring MVC configuration to support multiple languages. The configuration includes:

-   **`messageSource()`** → loads translation files (`messages_xx.properties`) in UTF-8.
-   **`localeResolver()`** → sets the default language (English) and stores the user’s preference in a cookie.
-   **`localeChangeInterceptor()`** → allows changing the language via the URL parameter `?lang=xx`.
-   **`addInterceptors()`** → registers the interceptor so it applies to all requests.

## Learning Outcomes

-   Learned how to implement internationalization in Spring Boot applications with Kotlin.
-   Gained experience modifying controllers and templates to support multiple languages.
-   Understood how to persist user language preferences using cookies and URL parameters.

## AI Disclosure

### AI Tools Used

-   ChatGPT
-   Grammarly
-   Copilot (VS Code)

### AI-Assisted Work

-   ChatGPT was used to translate `.properties` files, debug issues, and support the writing of this report.
-   Copilot in VS Code was used to speed up code writing with suggestions that were later reviewed and adapted.
-   Grammarly was used to improve the quality of the documentation in English.

### Percentage of AI-assisted Work & Modifications

Approximately **15% of the work** was AI-assisted, mainly translations of strings.  
The code suggested was reviewed, corrected, and manually adapted to fit the project’s specific requirements.

### Original Work

-   The implementation of the configuration class `I18nConfig.kt` and the integration with Thymeleaf were developed manually.
-   Original tests were added to verify the correct visualization of the application in Spanish and French.
-   Manual checks were performed with Postman, identifying an error in HTTP responses where the character `&` was encoded as `&amp;` after the changes.
-   External resources were consulted for guidance, including [this video](https://www.youtube.com/watch?v=p3FPz7Edx80&t=375s).
