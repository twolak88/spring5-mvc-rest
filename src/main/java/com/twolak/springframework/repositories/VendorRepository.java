/**
 * 
 */
package com.twolak.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twolak.springframework.domain.Vendor;

/**
 * @author twolak
 *
 */
public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
