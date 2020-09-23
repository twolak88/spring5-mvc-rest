/**
 * 
 */
package com.twolak.springframework.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twolak.springframework.spring5mvcrest.domain.Customer;

/**
 * @author twolak
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
