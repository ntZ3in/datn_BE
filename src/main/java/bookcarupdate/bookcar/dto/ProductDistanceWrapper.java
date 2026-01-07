package bookcarupdate.bookcar.dto;

import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDistanceWrapper {
    private Product product;
    private Trip trip;
    private double distance;
}
