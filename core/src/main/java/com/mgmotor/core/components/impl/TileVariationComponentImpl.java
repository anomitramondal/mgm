package com.mgmotor.core.components.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mgmotor.core.components.TileVariationComponent;
import com.mgmotor.core.components.bean.TileVariationComponentBean;


@Model(adaptables={SlingHttpServletRequest.class},
adapters={TileVariationComponent.class},
resourceType={TileVariationComponentImpl.RESOURCE_TYPE},
defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class TileVariationComponentImpl implements TileVariationComponent{
	
	protected static final String RESOURCE_TYPE = "mgmotor/components/content/tilevariations/v1/tilevariations";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private List<TileVariationComponentBean> bean = null;
	
	@Self
    @Required
    private SlingHttpServletRequest request;
	
	@ScriptVariable
    @Required
	private ResourceResolver resolver;
	
	@Self
    @Via("resource")
	@Required
	private Resource resource;
	
	@Inject
	@Via("resource")
	private List<Resource> cta;
	
	@PostConstruct
	protected void activate(){
		//TO-DO
	}
	
	@Override
	public List<TileVariationComponentBean> getCtaItems(){
		log.info("Inside getCtaItems method");
		if(null!=cta && !cta.isEmpty()){
			for(Resource res : cta){
				bean=new ArrayList<>();
				TileVariationComponentBean item = res.adaptTo(TileVariationComponentBean.class);
				bean.add(item);
			}
		}
		return bean;
	}

}
