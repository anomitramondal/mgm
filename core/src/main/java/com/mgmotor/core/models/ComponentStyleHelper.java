package com.mgmotor.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.mgmotor.core.utils.GlobalEnum;

@Model(adaptables = { SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class ComponentStyleHelper {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Self
	@Required
	private SlingHttpServletRequest request;

	@OSGiService
	private QueryBuilder queryBuilder;

	@SlingObject
	@Required
	private ResourceResolver resolver;

	@Self
	@Via("resource")
	private Resource resource;

	@ScriptVariable
	private Page resourcePage;

	@RequestAttribute
	private String styleIdParam;

	@ValueMapValue
	@Optional
	@Named("cq:styleIds")
	private String[] styleIds;

	private String resPageTemplatePath = null;
	private String compResTypePath = null;
	private String compPolicyResourcePath = null;
	private String cqPolicyPath = null;
	private String confRoot = null;
	private String compPolicyPath = null;

	private List<String> styleClasses = new ArrayList<>();

	@PostConstruct
	protected void activate() {
		log.info("Inside activate method==>" + resource.getPath());
		if (null != resource) {
			compResTypePath = resource.getResourceType();
			log.info("Component resource type==>" + compResTypePath);
			if (null != resourcePage) {
				resPageTemplatePath = resourcePage.getTemplate().getPath();
				log.info("Resource Page Template path==>" + resPageTemplatePath);
			}
		}

		if (null != resPageTemplatePath && null != compResTypePath) {
			compPolicyResourcePath = resPageTemplatePath + GlobalEnum.COMPONENT_POLICY_PATH_ROOT_FRAGMENT.getValue()
					+ "/" + compResTypePath;
			log.info("Component Policy Path==>" + compPolicyResourcePath);
			Resource compPolicyRes = resolver.getResource(compPolicyResourcePath);
			ValueMap vMap = compPolicyRes.adaptTo(ValueMap.class);
			if (vMap.containsKey("cq:policy")) {
				cqPolicyPath = vMap.get("cq:policy").toString();
			}
		}

		if (null != cqPolicyPath) {
			String splitString[] = resPageTemplatePath.split("/", 4);
			confRoot = "/" + splitString[1] + "/" + splitString[2];
			if (null != confRoot) {
				compPolicyPath = confRoot + GlobalEnum.TEMPLATE_POLICY_PATH_FRAGMENT.getValue() + "/" + cqPolicyPath;
				log.info("Policy Path of component inside template ==>" + compPolicyPath);
				Resource compPolicyRes = resolver.getResource(compPolicyPath);
				if (null != compPolicyRes) {
					Resource styleGroupsResource = compPolicyRes.getChild("cq:styleGroups");
					Map<String, String> predicateMap = new HashMap<>();
					styleClasses.clear();
					if (null != styleIds && styleIds.length > 0) {
						for (String styleId : styleIds) {
							predicateMap.clear();
							predicateMap.put("type", "nt:unstructured");
							predicateMap.put("path", styleGroupsResource.getPath());
							predicateMap.put("1_property", "cq:styleId");
							predicateMap.put("1_property.value", styleId);
							predicateMap.put("p.limit", "-1");

							Query queryObj = this.queryBuilder.createQuery(PredicateGroup.create(predicateMap),
									resolver.adaptTo(Session.class));
							SearchResult result = queryObj.getResult();
							Iterator<Resource> resources = result.getResources();
							while (resources.hasNext()) {
								Resource res = resources.next();
								ValueMap vMap = res.adaptTo(ValueMap.class);
								if (vMap.containsKey("cq:styleClasses"))
									styleClasses.add(vMap.get("cq:styleClasses").toString());
							}
						}
					}

				}
			}

		}
	}

	public List<String> getStyleClasses() {

		return styleClasses;
	}

	public boolean isStyleIdValid() {
		if (!styleClasses.isEmpty()) {
			if (styleClasses.contains(styleIdParam))
				return true;
			else
				return false;
		}
		log.info("No style configured for the component.");
		return false;
	}
}
