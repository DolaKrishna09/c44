package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.app.entity.Modules;



@RepositoryRestResource
public interface ModulesRepository extends JpaRepository<Modules, Integer> {

}
