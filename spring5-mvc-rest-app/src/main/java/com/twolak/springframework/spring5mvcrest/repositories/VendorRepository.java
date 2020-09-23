/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twolak.springframework.spring5mvcrest.domain.Vendor;

/**
 * @author twolak
 *
 */
public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
