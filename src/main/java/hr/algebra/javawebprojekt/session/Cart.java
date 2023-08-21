package hr.algebra.javawebprojekt.session;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    // Check if the cart item's product already exists in the cart.
    // If it does, update the quantity. Otherwise, add the item.
    public void addItem(CartItem item) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getIdProizvod().equals(item.getProduct().getIdProizvod()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
        } else {
            cartItems.add(item);
        }
    }

    // Find the cart item by product ID and then update its quantity if it exists.
    // If the update quantity is <= 0, remove the item from the cart.
    public void updateItemQuantity(int productId, int quantity) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getIdProizvod() == productId)
                .findFirst();

        if (existingItem.isPresent()) {
            if (quantity <= 0) {
                cartItems.remove(existingItem.get());
            } else {
                existingItem.get().setQuantity(quantity);
            }
        }
    }

    // Remove the cart item with the given product ID.
    public void removeItem(int productId) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().getIdProizvod() == productId);
    }

    // Remove all items from the cart
    public void removeAllItems() {
        cartItems.clear();
    }

    // Calculate the total price by multiplying each item's price by its quantity and summing them up.
    public float getTotal() {
        return (float) cartItems.stream()
                .mapToDouble(item -> item.getProduct().getCijena() * item.getQuantity())
                .sum();
    }
}

