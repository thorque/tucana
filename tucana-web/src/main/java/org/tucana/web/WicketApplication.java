package org.tucana.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 */
@Component(value = "wicketApplication")
public class WicketApplication extends WebApplication {
	
	 @Autowired
     private ApplicationContext applicationContext;
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<ConstellationList> getHomePage() {
		return ConstellationList.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		
		super.getComponentInstantiationListeners().add(new SpringComponentInjector(this,
				applicationContext, true));
		
        mountPage("/constellationList", ConstellationList.class);
        mountPage("/constellationDetail", ConstellationDetail.class);
	}
}
