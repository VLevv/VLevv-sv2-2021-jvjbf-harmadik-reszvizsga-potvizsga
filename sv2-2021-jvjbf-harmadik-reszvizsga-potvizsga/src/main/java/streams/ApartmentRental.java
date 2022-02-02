package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApartmentRental {
    private List<Apartment> apartments=new ArrayList<>();

    public void addApartment(Apartment apartment){
        apartments.add(apartment);
    }

    public List<Apartment> findApartmentByLocation(String location){
        return apartments.stream()
                .filter(apartment -> apartment.getLocation().equals(location))
                .collect(Collectors.toList());
    }

    public List<Apartment> findApartmentByExtras(String... extras){
        return apartments.stream()
                .filter(apartment -> apartment.getExtras().containsAll(new ArrayList<>(Arrays.asList(extras))))
                .collect(Collectors.toList());
    }

    public boolean isThereApartmentWithBathroomType(BathRoomType type){
        return apartments.stream()
                .anyMatch(apartment -> apartment.getBathRoomType().equals(type));
    }

    public List<Integer> findApartmentsSize(){
        return apartments.stream()
                .map(apartment -> apartment.getSize())
                .collect(Collectors.toList());
    }
}
