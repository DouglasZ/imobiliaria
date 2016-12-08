package com.br.imobiliaria.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * AbstractAnnotationConfigDispatcherServletInitializer - respons�vel por inicializar a aplicação 
 * (Spring com TomCat vão utilizar um recurso do Servlet 3 ou 3.1)
 * @author Douglas
 *
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppJPAConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {AppWebConfig.class};
	}

	/**
	 * Responsável por mapear o servlet da aplicação.
	 * Apartir do barra todas as requisições apartir do barra o servlet fica responsável
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        return new Filter[] { characterEncodingFilter };
	}
}
