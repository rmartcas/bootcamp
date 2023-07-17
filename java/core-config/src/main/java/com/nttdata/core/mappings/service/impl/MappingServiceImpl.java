/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.mappings.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import com.nttdata.core.cache.constants.CacheConstants;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.service.ComboService;
import com.nttdata.core.common.constants.CommonConstants;
import com.nttdata.core.common.exception.CoreException;
import com.nttdata.core.common.model.CoreUser;
import com.nttdata.core.common.model.DataLoad;
import com.nttdata.core.common.model.Page;
import com.nttdata.core.crud.mapper.CrudMapper;
import com.nttdata.core.mappingauthorities.service.MappingAuthorityService;
import com.nttdata.core.mappings.mapper.MappingMapper;
import com.nttdata.core.mappings.model.Mapping;
import com.nttdata.core.mappings.model.MappingDataLoad;
import com.nttdata.core.mappings.service.MappingService;
import com.nttdata.core.ui.service.UIService;
import com.nttdata.core.authorities.model.Authority;

/**
 * Implementation of mapping service
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Service
public class MappingServiceImpl implements MappingService {
	
	/** The associated mapping mapper */
	@Autowired
	private MappingMapper mapper;
	
	/** The combo service */
	@Autowired
	private ComboService comboService;
	
	/** The UI service */
	@Autowired
	private UIService uiService;
	
	/** Mapping - Authority service for entity association */
	@Autowired
	private MappingAuthorityService mappingAuthorityService;
	
	/** Handler mapping with all controller mappings */
	@Autowired
	@Qualifier("requestMappingHandlerMapping")
	private RequestMappingHandlerMapping handlerMapping;
	
	/**
	 * Return a {@link List}&lt;{@link Mapping}&gt; containing all application security patterns and security access expressions
	 * @return  {@link List}&lt;{@link Mapping}&gt; with all application mappings and their security expression to access
	 * @throws CoreException if error
	 */
	@Override
	public List<Mapping> getApiMappings() throws CoreException {
        return getApiMappings(false);
	}
	
    /**
     * Return a {@link List}&lt;{@link Mapping}&gt; containing all application security patterns and security access expressions
     * @param addRolePrefix {@link Boolean} when true, all authorities in the mapping list will be returned with "ROLE_" prefix plus authority name.
     * @return  {@link List}&lt;{@link Mapping}&gt; with all application mappings and their security expression to access
     * @throws CoreException if error
     */
    @Override
    @Cacheable(value = CacheConstants.CORE_CACHE, key = "'api-mappings'.concat(#addRolePrefix)")
    public List<Mapping> getApiMappings(boolean addRolePrefix) throws CoreException {
        List<Mapping> mappings = mapper.getApiMappings();
        if (addRolePrefix) {
            mappings = mappings.stream().map(s-> {
                s.getAuthorities().forEach(a -> a.setName("ROLE_" + a.getName()));
                return s;
            }).collect(Collectors.toList());
        }
        return mappings;
    }
	
	/**
	 * Return a {@link List}&lt;{@link String}&gt; containing all authorized mapping patterns
	 * allowed by the user profile.
	 *
	 * @param user {@link CoreUser} the current authenticated user
	 * @return {@link List}&lt;{@link String}&gt; with all accesible endpoints by the user
	 * @throws CoreException if any error ocurrs during method call
	 */
	@Override
	@Cacheable(value = CacheConstants.CORE_CACHE, key = "'mappings'.concat(#user.profileId)")
	public List<String> getUserMappings(CoreUser user) throws CoreException {
		return mapper.getUserMappings(user.getProfileId());
	}
	
	@Override
	public CrudMapper<Mapping> getMapper() {
		return this.mapper;
	}
	
	@Override
	public DataLoad init(Page<Mapping> dto) {
		MappingDataLoad data = new MappingDataLoad();
		data.setAuthorities(getAuthorities());
		data.setButtons(uiService.getButtons("mappings.init"));
		data.setColumns(uiService.getColumns("mappings.init"));
		return data;
	}
	
	@Override
	public DataLoad init(Mapping dto) {
		MappingDataLoad data = new MappingDataLoad();
		data.setAuthorities(getAuthorities());
		data.setMappings(getPathMappings());
		data.setButtons(uiService.getButtons("mappings.initedit"));
        return data;
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void insert(Mapping dto) {
		MappingService.super.insert(dto);
		
		// Insert all associated authorities with the new mapping
		mappingAuthorityService.insertAuthorities(dto);
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void update(Mapping dto) {
		MappingService.super.update(dto);
		
		// Delete all associated authorities
		mappingAuthorityService.deleteAuthorities(dto);
		// Re Insert all associated authorities with the updated mapping
		mappingAuthorityService.insertAuthorities(dto);
	}
	
	@Override
	@CacheEvict(cacheNames = CacheConstants.CORE_CACHE, allEntries = true)
	public void delete(Mapping dto) {
		// Delete all associated authorities
		mappingAuthorityService.deleteAuthorities(dto);
		
		MappingService.super.delete(dto);
	}
	
	@Override
	public Page<Mapping> search(Page<Mapping> dto) throws CoreException {
		Page<Mapping> page = MappingService.super.search(dto);
		computeAbsentMappings(page);
		return page;
	}
	
	private void computeAbsentMappings(Page<Mapping> page) {
    	Set<String> tree = getTreePathMappings();
    	
    	for (String path : tree) {
			Mapping m = page.getRecords().stream().filter(mapping -> mapping.getPattern().equalsIgnoreCase(path))
			.findFirst().orElse(null);
			
			if (null == m) {
				m = new Mapping();
				m.setPattern(path);
				page.getRecords().add(m);
			}
		}
    	page.setTotalRecords(page.getRecords().size());
	}
	
	private List<Combo> getAuthorities() {
		ComboPage combo = new ComboPage();
		combo.setTable("core_authorities");
		combo.setKey("authority_id");
		combo.setValue("name");
		return comboService.search(combo);
	}
	
    private List<Combo> getPathMappings() {
    	Set<String> tree = getTreePathMappings();
        
        List<Combo> paths = new ArrayList<>();
        for (String string : tree) {
            Combo c = new Combo();
            c.setId(string);
            c.setName(string);
            paths.add(c);
        }
        return paths;
    }
    
    private Set<String> getTreePathMappings() {
		Set<Entry<RequestMappingInfo, HandlerMethod>> entrySet = handlerMapping.getHandlerMethods().entrySet();
        Set<String> tree = new TreeSet<>();
        tree.add(CommonConstants.FORWARD_SLASH);
        for (Entry<RequestMappingInfo, HandlerMethod> entry : entrySet) {
            PathPatternsRequestCondition patternsCondition = entry.getKey().getPathPatternsCondition();
        	if (null != patternsCondition) {
                Set<PathPattern> patterns = patternsCondition.getPatterns();
        		addMappingPaths(tree, patterns);
        	}
        }
		return tree;
	}
    
    private void addMappingPaths(Set<String> tree, Set<PathPattern> patterns) {
		for (PathPattern path : patterns) {
			int start = 0;
			for (int end; (end = path.getPatternString().indexOf(CommonConstants.FORWARD_SLASH, start)) != -1; start = end + 1) {
		    	if (!CommonConstants.EMPTY_STRING.equalsIgnoreCase(path.getPatternString().substring(0, end))) {
		    		tree.add(path.getPatternString().substring(0, end) + CommonConstants.FORWARD_SLASH + "**");
		    	}
		    }
			tree.add(path.getPatternString());
		}
	}
    
    @Override
    public Map<String, List<String>> validateMappings() throws CoreException {
        Set<String> tree = getTreePathMappings();
        Map<String, List<String>> report = new TreeMap<>();
        Map<String, Set<Long>> pathAuth = new TreeMap<>();
        PathMatcher pathMatcher = handlerMapping.getPathMatcher();
        List<Mapping> apiMappings = getApiMappings();
        
        for (Mapping mapping : apiMappings) {
            for (String path : tree) {
            	pathAuth.computeIfAbsent(path, p -> new HashSet<>());
                if (pathMatcher.match(mapping.getPattern(), path.replace("/**", ""))) {
                    pathAuth.get(path).addAll(mapping.getAuthorities().stream().map(Authority::getId).collect(Collectors.toSet()));
                }
            }
        }
        
        pathAuth.forEach((k,v) -> {
            if (v.isEmpty()) {
                report.put(k, new ArrayList<>());
            } else {
                report.put(k, this.mapper.getProfilesByAuthorities(v));    
            }
        });
        return report;
    }
}
