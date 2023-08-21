package hr.algebra.javawebprojekt.session;

import hr.algebra.javawebprojekt.domain.Proizvod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Proizvod product;
    private int quantity;
}

