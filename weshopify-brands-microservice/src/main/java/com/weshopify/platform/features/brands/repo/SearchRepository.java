package com.weshopify.platform.features.brands.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.weshopify.platform.features.brands.domain.Brand;

@NoRepositoryBean
public interface SearchRepository<Brand, Integer> extends PagingAndSortingRepository<Brand, Integer> {
	public Page<Brand> findAll(String keyword, Pageable pageable);
}
