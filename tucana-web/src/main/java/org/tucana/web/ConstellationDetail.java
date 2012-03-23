/**
 * 
 */
package org.tucana.web;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.tucana.domain.Constellation;
import org.tucana.service.ConstellationService;

/**
 * @author kamann
 *
 */
public class ConstellationDetail extends WebPage {
	private String code;
	private Constellation constellation = null;
	private Locale locale;
	
	@SpringBean
	private ConstellationService service;

	/**
	 * @param parameters
	 */
	public ConstellationDetail(final PageParameters parameters) {
		super(parameters);
		this.code = parameters.get("code").toString();
		
		if (code.length() == 3){
			this.locale = getSession().getLocale();
			constellation = service.findConstellationByCode(code);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		addTitle();
		addDataLabels();
	}
	
	protected void addTitle(){
		add(new Label("title", new StringResourceModel("content.title", null,
				new Object[] { String.valueOf(constellation.getName()) })));
	}
	
	protected void addDataLabels(){
		NumberFormat nf = NumberFormat.getNumberInstance(getLocale());
		
		add(new Label("cd_name_label", constellation.getName()));
		add(new Label("cd_code_label", constellation.getCode().toUpperCase()));
		add(new Label("cd_gen_name_label", constellation.getGenitiveName()));
		add(new Label("cd_author_label", constellation.getAuthor()+" ("+constellation.getAuthorYear()+")"));
		add(new Label("cd_hemisphere_label", constellation.getHemisphere()));
		add(new Label("cd_area_label", nf.format(constellation.getArea())));
		add(new Label("cd_description_label", constellation.getDescription()).setEscapeModelStrings(false));
	}
	
	

}
