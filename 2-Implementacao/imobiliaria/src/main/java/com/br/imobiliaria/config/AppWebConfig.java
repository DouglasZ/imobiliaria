package com.br.imobiliaria.config;

import java.math.BigDecimal;
import java.util.Locale;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.br.imobiliaria.controller.ImoveisController;

/**
 * Classe de configuração do Spring deve ser anotada como @Configuration
 * Spring MVC deve usar o @EnableWebMvc
 * WebMvcConfigurerAdapter - já tras algumas configurações prontas
 * Precisamos definir onde o AppWebConfig irá encontrar as classes Controllers para criar os Beans
 * 
 * @author Douglas
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {ImoveisController.class}) //Procura todas as classes que est�o no mesmo pacote ImoveisController.class
public class AppWebConfig extends WebMvcConfigurerAdapter
{
	@Bean // Criando um objeto - Bean = Objeto
	public TemplateResolver webTemplateResolver() 
	{
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public TemplateEngine templateEngine(TemplateResolver templateResolver) 
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new LayoutDialect());
		
		return templateEngine;
	}
	
	/**
	 * Para criar o ViewResolver precisa ser criado o TemplateResolver e o TemplateEngine
	 * @param templateEngine
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) 
	{
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}
	
	@Bean
	public LocaleResolver localeResolver() 
	{
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	/**
	 * Utilizado conseguir achar os caminhos dos recursos est�ticos e carregar/entregar para servidor de aplica��o (TomCat) 
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) 
	{
		configurer.enable();
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);

		return conversionService;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("/WEB-INF/i18n/messages");
		bundle.setDefaultEncoding("UTF-8"); // http://www.utf8-chartable.de/
		bundle.setCacheSeconds(1);
		return bundle;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
	    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
	    validatorFactoryBean.setValidationMessageSource(messageSource());
	    return validatorFactoryBean;
	}
	
	@Override
	public Validator getValidator() {
		return validator();
	}
}
