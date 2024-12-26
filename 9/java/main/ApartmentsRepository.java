package main;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ApartmentsRepository extends JpaRepository<Apartment, Long> {

    @Modifying
    @Query("UPDATE Apartment a SET a.district= :district, a.address = :address, a.area = :area, a.countOfRooms = :countOfRooms, a.price = :price WHERE a.id = :id")
    void updateApartment (@Param("district") String district, @Param("address") String address, @Param("area") double area, @Param("countOfRooms") int countOfRooms, @Param("price") double price, @Param("id") long id);


    @Query("SELECT a FROM Apartment a WHERE a.price > :bottomBar AND a.price < :topBar")
  List<Apartment> findByPrice(@Param("bottomBar") double bottomBar, @Param("topBar") double topBar, Pageable pageable);

}
