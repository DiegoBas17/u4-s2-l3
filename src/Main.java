import com.github.javafaker.Faker;
import entities.Customer;
import entities.Order;
import entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        System.out.println("----------------------------------------es1-----------------------------------------------");

        Supplier<Product> productSupplierBook = () -> {
            Faker f = new Faker(Locale.ITALY);
            Random random = new Random();
            return new Product(f.book().title(), "Books", random.nextDouble() * 1000);
        };
        Supplier<Product> productSupplierBaby = () -> {
            Faker f = new Faker(Locale.ITALY);
            Random random = new Random();
            return new Product(f.funnyName().name(), "Baby", random.nextDouble() * 1000);
        };
        Supplier<Product> productSupplierBoys = () -> {
            Faker f = new Faker(Locale.ITALY);
            Random random = new Random();
            return new Product(f.pokemon().name(), "Boys", random.nextDouble() * 1000);
        };

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            products.add(productSupplierBaby.get());
            products.add(productSupplierBook.get());
            products.add(productSupplierBoys.get());
        }
        List<Product> booksCategoryExpensive = products.stream().filter(product -> product.getCategory() == "Books" && product.getPrice() > 100).toList();
        booksCategoryExpensive.forEach(System.out::println);

        System.out.println("------------------------------------------------es2-----------------------------------------");
        Customer gabriel = new Customer("Gabriel", 1);
        Customer arianna = new Customer("Arianna", 2);
        Customer eddy = new Customer("Eddy", 1);
        List<Product> productsGabriel = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            productsGabriel.add(productSupplierBook.get());
            productsGabriel.add(productSupplierBoys.get());
        }
        List<Product> productsArianna = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productsArianna.add(productSupplierBook.get());
            productsArianna.add(productSupplierBaby.get());
        }
        List<Product> productsEddy = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productsEddy.add(productSupplierBaby.get());
            productsEddy.add(productSupplierBook.get());
            productsEddy.add(productSupplierBoys.get());
        }
        Order gabrielShop = new Order("inviato", productsGabriel, gabriel);
        Order ariannaShop = new Order("inviato", productsArianna, arianna);
        Order eddyShop = new Order("inviato", productsEddy, eddy);

        List<Order> totalOrders = new ArrayList<>();
        totalOrders.add(gabrielShop);
        totalOrders.add(ariannaShop);
        totalOrders.add(eddyShop);

        List<Order> ordersWithBabyProducts = totalOrders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> "Baby".equals(product.getCategory())))
                .toList();

        ordersWithBabyProducts.forEach(System.out::println);
        System.out.println("----------------------------es3-----------------------------------------");
        List<Product> saleBoys = products.stream()
                .filter(product -> product.getCategory() == "Boys")
                .map(product -> new Product(product.getName(), product.getCategory(), product.setDiscount()))
                .toList();

        saleBoys.forEach(System.out::println);
        System.out.println("----------------------------es4-----------------------------------------");
        List<Product> tier2 = totalOrders.stream()
                .filter(order -> order.getCustomer().getTier() == 2)
                .flatMap(order -> order.getProducts().stream())
                .toList();

        tier2.forEach(System.out::println);
    }
}