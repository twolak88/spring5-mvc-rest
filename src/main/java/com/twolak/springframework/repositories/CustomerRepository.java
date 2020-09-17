/**
 * 
 */
package com.twolak.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twolak.springframework.domain.Customer;

/**
 * @author twolak
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
