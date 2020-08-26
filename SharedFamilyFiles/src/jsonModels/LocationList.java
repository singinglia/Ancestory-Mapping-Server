package jsonModels;

import java.util.List;

public class LocationList {
    private List<Location> locations;

    public LocationList(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
