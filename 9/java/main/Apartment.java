package main;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false)
    private String district;

    @Column (nullable = false)
    private String address;

    @Column (nullable = false)
    private double area;

    @Column (nullable = false)
    private int countOfRooms;

    @Column (nullable = false)
    private double price;

    public Apartment() {
    }

    public Apartment(String district, String address, double area, int countOfRooms, double price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.countOfRooms = countOfRooms;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public double getArea() {
        return area;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public double getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Apartments{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", countOfRooms=" + countOfRooms +
                ", price=" + price +
                '}';
    }
}
