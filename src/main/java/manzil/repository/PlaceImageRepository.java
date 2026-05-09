package manzil.repository;

import manzil.model.PlaceImage;
import manzil.model.id.PlaceImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceImageRepository extends JpaRepository<PlaceImage, PlaceImageId>
{
    
}
