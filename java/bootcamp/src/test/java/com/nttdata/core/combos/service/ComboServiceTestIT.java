/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.combos.service;

import static org.hamcrest.Matchers.hasItems;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.nttdata.BaseTest;
import com.nttdata.core.combos.model.Combo;
import com.nttdata.core.combos.model.ComboPage;
import com.nttdata.core.combos.model.ComboCriteria;
import com.nttdata.core.combos.model.ComboJoinTable;

class ComboServiceTestIT extends BaseTest {
	
	@Autowired
	private ComboService comboService;

	@Test
	void testGetComboFromNonExistingPropertyReturnEmptyList() throws Exception {
		ComboPage comboPage = new ComboPage();
		comboPage.setKey("i18n.invalid.key");
		List<Combo> search = comboService.search(comboPage , true);
		
		Assertions.assertNotNull(search);
		Assertions.assertTrue(search.isEmpty());
	}
	
	@Test
	void testGetComboFromExistingPropertyReturnList() throws Exception {
		LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
		ComboPage comboPage = new ComboPage();
		comboPage.setKey("i18n.messages.yes.no");
		List<Combo> search = comboService.search(comboPage , true);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		Combo comboSi = new Combo();
		comboSi.setId("Yes");
		comboSi.setName("Yes");
		
		Combo comboNo = new Combo();
		comboNo.setId("No");
		comboNo.setName("No");
		MatcherAssert.assertThat(search, hasItems(comboSi, comboNo));
	}
	
	@Test
	void testGetComboFromExistingPropertyWithKeyValueReturnList() throws Exception {
		LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
		ComboPage comboPage = new ComboPage();
		comboPage.setKey("i18n.messages.true.false");
		List<Combo> search = comboService.search(comboPage , true);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		Combo comboTrue = new Combo();
		comboTrue.setId("true");
		comboTrue.setName("True");
		
		Combo comboFalse = new Combo();
		comboFalse.setId("false");
		comboFalse.setName("False");
		MatcherAssert.assertThat(search, hasItems(comboTrue, comboFalse));
	}
	
	@Test
	void testGetComboWithNullCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
	}
	
	@Test
	void testGetComboWithEmptyCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
	}
	
	@Test
	void testGetComboWithEqualCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.EQUALS, 1L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		Assertions.assertEquals(1, search.size());
		
		for (Combo result : search) {
			Assertions.assertEquals(String.valueOf(1L), result.getId());
		}
	}
	
	@Test
	void testGetComboWithNotEqualCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.NOT_EQUALS, 1L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertNotEquals(String.valueOf(1L), result.getId());
		}
	}
	
	@Test
	void testGetComboWithGreaterCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.GREATER, 0L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(Long.valueOf(result.getId()) > 0L);
		}
	}
	
	@Test
	void testGetComboWithGreaterOrEqualCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.GREATER_OR_EQUAL, 1L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(Long.valueOf(result.getId()) >= 1L);
		}
	}
	
	@Test
	void testGetComboWithLessCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.LESS, 3L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(Long.valueOf(result.getId()) < 3L);
		}
	}
	
	@Test
	void testGetComboWithLessOrEqualCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.LESS_OR_EQUAL, 3L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(Long.valueOf(result.getId()) <= 3L);
		}
	}
	
	@Test
	void testGetComboWithContainsCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("name", ComboCriteria.CriteriaOperator.CONTAINS, "admin"));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(result.getName().toLowerCase().contains("admin"));
		}
	}
	
	@Test
	void testGetComboWithInCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		List<Long> asList = Arrays.asList(1L, 3L);
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.IN, asList));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertTrue(asList.contains(Long.valueOf(result.getId())));
		}
	}
	
	@Test
	void testGetComboWithInCriteriaButValueIsNotIterable() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.IN, 1L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertTrue(search.isEmpty());
	}
	
	@Test
	void testGetComboWithNotInCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		List<Long> asList = Arrays.asList(1L, 3L);
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.NOT_IN, asList));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertFalse(asList.contains(Long.valueOf(result.getId())));
		}
	}
	
	@Test
	void testGetComboWithNotInCriteriaButValueIsNotIterable() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("profile_id");
		combo.setValue("name");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("profile_id", ComboCriteria.CriteriaOperator.NOT_IN, 1L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertTrue(search.isEmpty());
	}
	
	@Test
	void testGetComboWithIsNullCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_menus");
		combo.setKey("parent_menu_id");
		combo.setValue("title");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("parent_menu_id", ComboCriteria.CriteriaOperator.IS_NULL, null));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertNull(result.getId());
		}
	}
	
	@Test
	void testGetComboWithIsNotNullCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_menus");
		combo.setKey("parent_menu_id");
		combo.setValue("title");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("parent_menu_id", ComboCriteria.CriteriaOperator.IS_NOT_NULL, null));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertNotNull(result.getId());
		}
	}
	
	@Test
	void testGetComboWithMultipleCriteria() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_menus");
		combo.setKey("parent_menu_id");
		combo.setValue("title");
		combo.setCriteria(new ArrayList<>());
		
		combo.getCriteria().add(new ComboCriteria("title", ComboCriteria.CriteriaOperator.CONTAINS, "menu"));
		combo.getCriteria().add(new ComboCriteria("parent_menu_id", ComboCriteria.CriteriaOperator.IS_NOT_NULL, null));
		combo.getCriteria().add(new ComboCriteria("link", ComboCriteria.CriteriaOperator.EQUALS, "authorities"));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertNotNull(result.getId());
			Assertions.assertTrue(result.getName().contains("menu"));
			Assertions.assertTrue(result.getName().contains("authorities"));
		}
	}
	
	@Test
	void testComboJoinOtherTable() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_profiles");
		combo.setKey("core_profile_authorities.authority_id");
		combo.setValue("name");
		
		combo.setJoins(new ArrayList<>());
		combo.getJoins().add(new ComboJoinTable("core_profile_authorities",
				ComboJoinTable.JoinType.INNER_JOIN, "profile_id", "profile_id"));
		
		combo.setCriteria(new ArrayList<>());
		combo.getCriteria().add(new ComboCriteria("authority_id", ComboCriteria.CriteriaOperator.EQUALS, 3L));
		
		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
		
		for (Combo result : search) {
			Assertions.assertEquals(Long.valueOf(3L), Long.valueOf(result.getId()));
		}
	}
	
	@Test
	void testGetComboWithGroupBy() throws Exception {
		ComboPage combo = new ComboPage();
		combo.setTable("core_menus");
		combo.setKey("menu_id");
		combo.setValue("title");
		combo.setGroupBy(new ArrayList<>());
		
		combo.getGroupBy().add("menu_id");
		combo.getGroupBy().add("title");

		List<Combo> search = comboService.search(combo);
		
		Assertions.assertNotNull(search);
		Assertions.assertFalse(search.isEmpty());
	}
}
