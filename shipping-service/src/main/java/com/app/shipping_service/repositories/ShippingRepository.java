package com.app.shipping_service.repositories;

import com.app.shipping_service.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {

    Optional<Shipping> findByOrderId(Long orderId);

}
