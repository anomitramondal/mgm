package com.mgmotor.core.components.bean;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

/**
 * @author Anomitra
 *
 */
@Model(
		adaptables={Resource.class},
		defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class FeaturedContentAdditionalLinks {
	
	@Inject
	private String ctatitle;
	
	@Inject
	private String url;
	
	@Inject
	private String urlParams;
	
	@Inject
	private String target;
	
	@Inject
	private String anchorTag; 
	
	@Inject
	private String theme;
	
	@Inject
	private String alignment;
	

	public String getCtatitle() {
		return ctatitle;
	}

	public void setCtatitle(String ctatitle) {
		this.ctatitle = ctatitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(String urlParams) {
		this.urlParams = urlParams;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAnchorTag() {
		return anchorTag;
	}

	public void setAnchorTag(String anchorTag) {
		this.anchorTag = anchorTag;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	
	
	
	
}
