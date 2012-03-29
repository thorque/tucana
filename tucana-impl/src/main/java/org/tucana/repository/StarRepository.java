/**
 * 
 */
package org.tucana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tucana.domain.Star;

/**
 * @author kamann
 *
 */
public interface StarRepository extends JpaRepository<Star, Long> {
	
	List<Star> findByCode(String code);

}
