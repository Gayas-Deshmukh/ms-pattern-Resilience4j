package com.weshopify.platform.features.brands.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.weshopify.platform.features.brands.bean.BrandBean;
import com.weshopify.platform.features.brands.domain.Brand;
import com.weshopify.platform.features.brands.exceptions.BrandNotFoundException;
import com.weshopify.platform.features.brands.outbound.CartegoryFeignClient;
import com.weshopify.platform.features.brands.outbound.CategoriesOutboundService;
import com.weshopify.platform.features.brands.repo.BrandRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandService {
	public static final int BRANDS_PER_PAGE = 10;

	@Autowired
	private BrandRepository repo;
	
	@Autowired
	private CartegoryFeignClient categoryFeignClient;
	
	@Autowired
	private CategoriesOutboundService categoryOutboundService;
	
	public List<Brand> listAll() {
		return (List<Brand>) repo.findAll();
	}

	public List<Brand> listByPage(int pageNum, int noOfRecPerPage) {
		Pageable pageable = PageRequest.of(pageNum, noOfRecPerPage);
		// helper.listEntities(pageNum, BRANDS_PER_PAGE, repo);
		return repo.findAll(pageable).getContent();
	}

	/*
	 * public BrandBean save(BrandBean brandBean) {
	 * 
	 * Brand brand = mapBeanToDomain(brandBean); brand = repo.save(brand); brandBean
	 * = mapDomainToBean(brand); return brandBean; }
	 */
	public BrandBean save(BrandBean brandBean, String logo)
	{
		Brand brandDomain = mapBeanToDomain(brandBean); 
		brandDomain.setLogo(logo);
		brandDomain = repo.save(brandDomain);

		brandBean  = mapDomainToBean(brandDomain); 
		return brandBean;
	}

	public BrandBean save(BrandBean brandBean) {
		Brand brandDomain = new Brand();
		BeanUtils.copyProperties(brandBean, brandDomain);
		brandDomain = repo.save(brandDomain);
		BeanUtils.copyProperties(brandDomain, brandBean);
		return brandBean;
	}

	private BrandBean mapDomainToBean(Brand brandDomain) 
	{
		BrandBean brandBean = new BrandBean();
		BeanUtils.copyProperties(brandDomain, brandBean);
		//Set<Category> catset = brandDomain.getCategories();
		List<String> catOriginalData = new ArrayList<>();
		String catset = brandDomain.getCategories();
		
		if (catset != null) 
		{
			JSONArray array = new JSONArray(catset);
			
			for(int i=0;i<array.length();i++) 
			{
				JSONObject jsonOb = array.getJSONObject(i);
				int catId = jsonOb.getInt("id");
				// String catData = categoryOutboundService.findCategoryById(catId);
				ResponseEntity<String> catResp = categoryFeignClient.findCategoryById(catId);
				
				if (catResp.getStatusCode() == HttpStatus.OK)
				{
					String catData = catResp.getBody();
					catOriginalData.add(catData);
				}
				else 
				{
					return null;
				}
			}
			
			brandBean.setCategories(Arrays.asList(catset));
		}
		
		brandBean.setLogo(brandDomain.getLogo());
		return brandBean;
	}
	
	private Brand mapBeanToDomain(BrandBean brandBean) 
	{
		Brand brand = new Brand();
		BeanUtils.copyProperties(brandBean, brand);
		
		List<String> catOriginalData = new ArrayList<>();
		List<Object> catset          = brandBean.getCategories();
		
		if (catset != null)
		{
			JSONArray array = new JSONArray(catset);
			
			for(int i=0;i<array.length();i++) 
			{
				JSONObject jsonOb = array.getJSONObject(i);
				int catId = jsonOb.getInt("id");
				// String catData = categoryOutboundService.findCategoryById(catId);
				ResponseEntity<String> catResp = categoryFeignClient.findCategoryById(catId);
				
				if (catResp.getStatusCode() == HttpStatus.OK)
				{
					String catData = catResp.getBody();
					catOriginalData.add(catData);
				}
			}
			/*
			 * Set<Category> catDomainSet = new HashSet<>(); catset.forEach(catBean -> {
			 * Category catDomain = new Category(); BeanUtils.copyProperties(catBean,
			 * catDomain); catDomainSet.add(catDomain);
			 * 
			 * });
			 */
			brand.setCategories(catOriginalData.toString());
		}
		
		return brand;
	}
	
	
	public BrandBean get(Integer id)
	{
     /*		try 
		{
			Optional<Brand> optional = repo.findById(id);
			if (optional.isPresent()) 
			{
				log.info("Brand with the ID :\t" + id + "\t found");
				Brand brandDomain = optional.get();
				BrandBean brandBean = mapDomainToBean(brandDomain);
				return brandBean;
			}
			
			throw new BrandNotFoundException("Could not find any brand with ID " + id);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new BrandNotFoundException("Could not find any brand with ID " + id);
		}  */
		
		Optional<Brand> optional = repo.findById(id);
		if (optional.isPresent()) 
		{
			log.info("Brand with the ID :\t" + id + "\t found");
			Brand brandDomain = optional.get();
			BrandBean brandBean = mapDomainToBean(brandDomain);
			return brandBean;
		}
		
		return null;
	}

	public List<Brand> delete(Integer id) throws BrandNotFoundException {
		Long countById = repo.countById(id);

		if (countById == null || countById == 0) {
			throw new BrandNotFoundException("Could not find any brand with ID " + id);
		}

		repo.deleteById(id);

		return listAll();

	}

	public String checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Brand brandByName = repo.findByName(name);

		if (isCreatingNew) {
			if (brandByName != null)
				return "Duplicate";
		} else {
			if (brandByName != null && brandByName.getId() != id) {
				return "Duplicate";
			}
		}

		return "OK";
	}
}
