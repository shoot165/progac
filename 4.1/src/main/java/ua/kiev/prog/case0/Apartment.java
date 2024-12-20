package ua.kiev.prog.case0;


public class Apartment {


    @Id
    private int id;


    private String district;
    private String address;
    private int area;
    private int countOfRooms;
    private int price;


    public Apartment() {
    }

    public Apartment(String district, String address, int area, int countOfRooms, int price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.countOfRooms = countOfRooms;
        this.price = price;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {}

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", countOfRooms=" + countOfRooms +
                ", price=" + price +
                '}';
    }
}
