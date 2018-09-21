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

import com.mgmotor.core.components.FeaturedContentComponent;
import com.mgmotor.core.components.bean.FeaturedContentAdditionalLinks;


/**
 * @author uiam82
 *
 */
@Model(
		adaptables={SlingHttpServletRequest.class},
		adapters={FeaturedContentComponent.class},
		resourceType={FeaturedContentComponentImpl.RESOURCE_TYPE},
		defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL
		)
public class FeaturedContentComponentImpl implements FeaturedContentComponent{

	protected static final String RESOURCE_TYPE = "mgmotor/components/content/featuredcontent/v1/featuredcontent";
	private List<FeaturedContentAdditionalLinks> listAddLinks=new ArrayList<>();
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Self
    @Required
    private SlingHttpServletRequest request;
	
	@ScriptVariable
    @Required
	private ResourceResolver resolver;
	
	@Self
    @Via("resource")
	private Resource resource;
	
	@Inject
	@Via("resource")
	private List<Resource> additionalLinks;
	
	private String nodePath;
	
	@PostConstruct
	public void init(){
		
		this.nodePath = resource.getPath();
	}


	@Override
	public List<FeaturedContentAdditionalLinks> getAdditionalLinks() {
		
		/*This will be used when multifields will be stored as json*/
			/*MultiFieldHelper<FeaturedContentAdditionalLinks> multiHelper = new MultiFieldHelper<>(nodePath, "additionalLinks", FeaturedContentAdditionalLinks.class, resourceResolver);
			try {
				listAddLinks = multiHelper.populateBeanList();
			} catch (IllegalStateException | IOException | RepositoryException | LoginException e) {
				log.error("Error Encountered while parsing json===>",e);
			}*/
		listAddLinks.clear();
		for(Resource link : additionalLinks){
			if(null!=link){
				FeaturedContentAdditionalLinks featuredLink = link.adaptTo(FeaturedContentAdditionalLinks.class);
				listAddLinks.add(featuredLink);
			}
		}
		return listAddLinks;
	}
	

	
}
