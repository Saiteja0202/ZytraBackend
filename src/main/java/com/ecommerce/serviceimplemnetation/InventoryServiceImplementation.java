package com.ecommerce.serviceimplemnetation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Categories;
import com.ecommerce.entities.SubCategory;
import com.ecommerce.repository.CategoriesRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.InventoryService;

@Service
public class InventoryServiceImplementation implements InventoryService {

	private final CategoriesRepository categoriesRepository;
	private final SubCategoryRepository subCategoryRepository;

	public InventoryServiceImplementation(CategoriesRepository categoriesRepository,
			SubCategoryRepository subCategoryRepository) {
		this.categoriesRepository = categoriesRepository;
		this.subCategoryRepository = subCategoryRepository;
	}

	@Override
	public ResponseEntity<String> addNewCategory(int adminId, Categories categories) {

		if (categoriesRepository.existsByCategoryName(categories.getCategoryName())) {
			return ResponseEntity.badRequest().body("Category already exists");
		}

		Categories newCategory = new Categories();
		newCategory.setCategoryName(categories.getCategoryName().toLowerCase());
		newCategory.setCategoryDescription(categories.getCategoryDescription());
		categoriesRepository.save(newCategory);

		return ResponseEntity.ok("Category added successfully");
	}

	@Override
	public ResponseEntity<String> addNewSubCategory(int adminId, SubCategory subCategory) {

		if (subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())) {
			return ResponseEntity.badRequest().body("Subcategory already exists");
		}
		
		SubCategory newSubCategory = new SubCategory();
		newSubCategory.setSubCategoryName(subCategory.getSubCategoryName().toLowerCase());
		newSubCategory.setSubCategoryDescription(subCategory.getSubCategoryDescription());
		newSubCategory.setCategoryId(subCategory.getCategoryId());
		subCategoryRepository.save(newSubCategory);

		return ResponseEntity.ok("Subcategory addded successfully");
	}
	
	
	@Override
	public 	ResponseEntity<?> getAllCategories(int adminId)
	{
		
		List<Categories> allCategories = categoriesRepository.findAll();
		
		ArrayList<Categories> listOfCategories = new ArrayList<>();
		for(Categories category : allCategories)
		{
			Categories categories = new Categories();
			categories.setCategoryId(category.getCategoryId());
			categories.setCategoryName(category.getCategoryName());
			categories.setCategoryDescription(category.getCategoryDescription());
			listOfCategories.add(categories);
		}
		
		if(listOfCategories == null )
		{
			return ResponseEntity.badRequest().body("Categories not found");
		}
		
		return ResponseEntity.ok(listOfCategories);
	}

}
