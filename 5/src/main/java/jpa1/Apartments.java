package jpa1;


import javax.persistence.*;

@Entity
@Table(name = "Apartments")
public class Apartments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false)
    private String district;

    @Column (nullable = false)
    private String address;

    @Column (nullable = false)
    private int area;

    @Column (nullable = false)
    private int countOfRooms;

    @Column (nullable = false)
    private int price;

    public Apartments() {
    }

    public Apartments(String district, String address, int area, int countOfRooms, int price) {
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

    public int getArea() {
        return area;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public int getPrice() {
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

    public void setArea(int area) {
        this.area = area;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public void setPrice(int price) {
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
