package com.htsec.mysql.dao;

import com.htsec.mysql.dto.Pet;

import java.util.List;


public interface PetMapper {
    List<Pet> getAllPets();
    public void insertByProc();

}