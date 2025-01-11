package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;

public class Shop {

    private double cash = 100.00;
    private Product[] inventory;
    private int numberProducts;
    private Sale[] sales;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new Product[10];
    }

    public static void main(String[] args) {
        ArrayList<Product> Productos = new ArrayList<>();
        Shop shop = new Shop();
        shop.loadInventory(Productos);
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        double totalAmount = 0.0;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Anyadir producto");
            System.out.println("3) Anyadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver el total de ventas agrupadas");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1://(#1)
                    shop.showCash();
                    break;

                case 2://(#3)
                    shop.addProduct(Productos);
                    break;

                case 3://(#5)
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5://(#2)
                    shop.showInventory(Productos);
                    break;

                case 6:
                    shop.sale(totalAmount);
                    break;

                case 7://(#6)
                    shop.showSales();
                
                case 8:
                    shop.showTotalSalesAmount();
                    break;
                    
                case 10://(#4)
                    System.out.println("Saliendo del programa...");
                    break;
            }
        } while (opcion != 10);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory(ArrayList<Product> Productos) {
        Productos.add(new Product("Manzana", 10.00, true, 10));
        Productos.add(new Product("Pera", 20.00, true, 20));
        Productos.add(new Product("Hamburguesa", 30.00, true, 30));
        Productos.add(new Product("Fresa", 5.00, true, 20));
    }

    /**
     * show current total cash
     */
    private void showCash() { //(#1)
        //LE FALTABA EL cash PARA QUE SALIERA EL DINERO
        System.out.println("Dinero actual: " + cash);

    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct(ArrayList<Product> Productos) { //(#2)
        if (isInventoryFull()) {
            System.out.println("No se pueden anyadir mas productos");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Productos.add(new Product(name, wholesalerPrice, true, stock));
        // AÑADIR LA CONDICION IF PARA QUE SI EXISTE QUE NO LO GUARDE.
        if (!"Manzana".equals(name) && !"Pera".equals(name) && !"Hamburguesa".equals(name) && !"Fresa".equals(name)) {
            addProduct(new Product(name, wholesalerPrice, true, stock));
        } else {
            System.out.println("No se puede ingressar este producto porque y existe");
        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() { //(#3)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a anyadir: ");
            int stock = scanner.nextInt();
            //PONER QUE CUANDO SE GUARDE LA CANTIDAD EN LA VARIABLE DE STOCK, SE ACTUALIZA SOBRE LA CANTIDAD QUE YA TENIA. 
            // SI TENIA 10 EN STOCK Y SE LE AÑADEN 4 -> HAY 14 EN STOCK
            stock += product.getStock();
            // update stock product
            product.setStock(stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() { //(#4)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);
        // PONER EL IF DEL AVAILABLE
        if (product != null) {
            System.out.print("Introduce la fecha de caducidad: ");
            boolean available = scanner.nextBoolean();
            if (available == true) {
                System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.isAvailable());
            } else {
                System.out.println("El stock del producto " + name + " ha sido actualizado a false.");
            }
        } else {
            System.out.println("No se puede anyadir la fecha de caducidad.");
        }
    }

    /**
     * show all inventory
     */
    public void showInventory(ArrayList<Product> Productos) { //(#2)
        /*
        CREAR UN ARRAY LIST EN EL MAIN EL CUAL TENGA RELACION CON EL ARRAY DE SALE, ENTONCES TENDREMOS
        UN ARRAY LIST DENTOR DEL ARRAY.
         */
        System.out.println("Contenido actual de la tienda:");
        for (int i = 0; i < Productos.size(); i++) {
            if (Productos != null) {
                System.out.print("NOMBRE: " + Productos.get(i).getName() + ", ");
                System.out.print("PRECIO MAYORISTA: " + Productos.get(i).getWholesalerPrice() + ", ");
                System.out.print("STOCK: " + Productos.get(i).getStock() + ".");
                System.out.println();
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale(double totalAmount) {
        // ask for client name
        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String client = sc.nextLine();

        // sale product until input name is not 0
        String name = "";
        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount += product.getPublicPrice();
                product.setStock(product.getStock() - 1);
                // if no more stock, set as not available to sale
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println("Producto anyadido con exito");
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        // show cost total
        totalAmount = totalAmount * TAX_RATE;
        cash += totalAmount;
        System.out.println("Venta realizada con exito, total: " + totalAmount);
    }

    /**
     * show all sales
     */
    private void showSales() { //(#6)
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sales != null) {
                System.out.println(sale);
            } else {
                System.out.println("No hay ventas registradas.");
            }
        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden anyadir mas productos, se ha alcanzado el maximo de " + inventory.length);
            return;
        }
        inventory[numberProducts] = product;
        numberProducts++;
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
    public boolean isInventoryFull() {
        if (numberProducts == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getName().equals(name)) {
                return inventory[i];
            }
        }
        return null;
    }

    /**
     * show  total sales amount
     */
    private void showTotalSalesAmount() {
    double totalSales = 0.0;
    if (sales != null) {
        for (Sale sale : sales) {
            if (sale != null) {
                totalSales += sale.getAmount();
            }
        }
    }
    System.out.println("Monto total de todas las ventas: " + totalSales);
}
}
