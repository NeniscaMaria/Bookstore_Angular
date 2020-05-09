package ro.ubb.catalog.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.web.dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class Console {
    public static final String clientURL = "http://localhost:8080/api/clients";
    public static final String bookURL = "http://localhost:8080/api/books";
    public static final String purchaseURL = "http://localhost:8080/api/purchases";
    public static final String sortURL = "http://localhost:8080/api/sort";
    @Autowired
    private RestTemplate template;
    //gitk and git gui in console

    public void runConsole() {
        boolean finished = false;
        while (!finished) {
            printChoices();
            try {
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Input your choice: ");
                int choice = keyboard.nextInt();
                switch (choice) {
                    case 0:
                        finished = true;
                        break;
                    case 1:
                        addClient();
                        break;
                    case 2:
                        addBook();
                        break;
                    case 3:
                        printAllClients();
                        break;
                    case 4:
                        printAllBooks();
                        break;
                    case 5:
                        filterClients();
                        break;
                    case 6:
                        filterBooks();
                        break;
                    case 7:
                        deleteClient();
                        break;
                    case 8:
                        deleteBook();
                        break;
                    case 9:
                        updateClient();
                        break;
                    case 10:
                        updateBook();
                        break;
                    case 11:
                        addPurchase();
                        break;
                    case 12:
                        displayPurchases();
                        break;
                    case 13:
                        updatePurchase();
                        break;
                    case 14:
                        deletePurchase();
                        break;
                    case 15:
                        filterPurchases();
                        break;
                    case 16:
                        getReport();
                        break;
                    case 17:
                        sortClients();
                        break;
                    case 18:
                        sortBooks();
                        break;
                    case 19:
                        sortPurchases();
                        break;
                    default:
                        throw new ValidatorException("Please input a valid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please input a number.");
            } catch (ValidatorException ve) {
                System.out.println(ve.getMessage());
            }

        }
    }

    private void printChoices(){
        System.out.println("\nChoose one from below:");
        System.out.println("0.Exit");
        System.out.println("1.Add new client.");
        System.out.println("2.Add new book.");
        System.out.println("3.Show all clients.");
        System.out.println("4.Show all books.");
        System.out.println("5.Filter clients.");
        System.out.println("6.Filter books.");
        System.out.println("7.Delete client.");
        System.out.println("8.Delete book.");
        System.out.println("9.Update client.");
        System.out.println("10.Update book.");
        System.out.println("11.Buy a book.");
        System.out.println("12.Show all purchases.");
        System.out.println("13.Update purchase.");
        System.out.println("14.Delete purchase.");
        System.out.println("15.Filter purchases.");
        System.out.println("16.Report.");
        System.out.println("17.Sort clients.");
        System.out.println("18.Sort books.");
        System.out.println("19.Sort purchases.");
    }

    //******************************************************************************************************************
    //Client functions:
    private void printAllClients() {
        ClientsDto allStudents = template.getForObject(clientURL, ClientsDto.class);
        assert allStudents != null;
        allStudents.getClients().forEach(System.out::println);
    }

    private void addClient() {
        Optional<Client> client = readClient();
        client.ifPresent(c->{
            try {
                ClientDto savedClient = template.postForObject(clientURL,
                        new ClientDto(c.getSerialNumber(),c.getName()),
                        ClientDto.class);
                System.out.println("Client saved = "+savedClient);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private void deleteClient(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            template.delete(clientURL + "/"+id.toString()+"");
        } catch (IOException  ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
    }

    private void updateClient(){
        Optional<Client> client = readClient();
        client.ifPresent(c->{
            try {
                template.put(clientURL+"/{id}",c,c.getId());
            } catch (ValidatorException  e) {
                System.out.println(e.getMessage());
            }});
    }

    private void filterClients() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Filter after: ");
            String name = bufferRead.readLine();
            System.out.println("filtered clients (name containing "+name+" ):");
            ClientsDto clients = template.getForObject(clientURL+"/filter/"+name+"",ClientsDto.class);
            assert clients != null;
            clients.getClients().forEach(System.out::println);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }

    }

    private Optional<Client> readClient() {
        //DESCR: function that reads a client from the keyboard
        //POST: returns the client read

        System.out.println("Please enter a new client: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            System.out.println("Serial Number: ");
            String serialNumber = bufferRead.readLine();
            System.out.println("Name: ");
            String name = bufferRead.readLine();

            Client student = new Client(serialNumber, name);
            student.setId(id);

            return Optional.of(student);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private void sortClients() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter how to order the elements: ");
        try {
            String order = bufferRead.readLine();
            Sort.Direction dir = Sort.Direction.DESC;
            System.out.println("Please enter your filters: ");
            String[] r = bufferRead.readLine().split(" ");
            ClientsDto clients = template.postForObject(sortURL+"/clients/"+dir+"",r,ClientsDto.class);
            assert clients != null;
            if(order.equals("ASC")) {
                List<ClientDto> c = new ArrayList<>(clients.getClients());
                Collections.reverse(c);
                c.forEach(System.out::println);
            }
            else
                clients.getClients().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------------------------------------
    // Books
    // -------------------------------------------------------------------------------------------------------------

    private void printAllBooks() {
        BooksDto allBooks = template.getForObject(bookURL, BooksDto.class);
        assert allBooks != null;
        allBooks.getBooks().forEach(System.out::println);
    }

    private void addBook() {
        Optional<Book> book = readBook();
        book.ifPresent(c->{
            try {
                BookDto savedBook = template.postForObject(bookURL,
                        new BookDto(c.getSerialNumber(),c.getTitle(),c.getAuthor(),c.getYear(),c.getPrice(),c.getInStock()),
                        BookDto.class);
                System.out.println("Book saved="+savedBook);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private void deleteBook(){
        // Delete the book with the same ID (read)
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");
        try {
            Long id = Long.parseLong(bufferRead.readLine());
            template.delete(bookURL+"/{id}",id);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private void updateBook(){
        // Update one or more attributes of the given book
        // IDs (read) must match
        Optional<Book> book = readBook();
        book.ifPresent(c->{
            try{
                template.put(bookURL+"/{id}",c,c.getId());
            }
            catch (ValidatorException  e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void filterBooks() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Filter by title: ");
        try {
            String filter = bufferRead.readLine();
            BooksDto filteredBooks = template.getForObject(bookURL+"/filter/"+filter+"",BooksDto.class);
            assert filteredBooks != null;
            filteredBooks.getBooks().forEach(System.out::println);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private Optional<Book> readBook() {
        // Input book from keyboard
        System.out.println("Please enter a new book: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            System.out.println("Serial Number: ");
            String serialNumber = bufferRead.readLine();
            System.out.println("Title: ");
            String name = bufferRead.readLine();
            System.out.println("Author: ");
            String author = bufferRead.readLine();
            System.out.println("Year: ");
            int year = Integer.parseInt(bufferRead.readLine());
            System.out.println("Price: ");
            double price = Double.parseDouble(bufferRead.readLine());
            System.out.println("In stock: ");
            int stock = Integer.parseInt(bufferRead.readLine());

            Book book = new Book(serialNumber, name, author, year, price, stock);
            book.setId(id);

            return Optional.of(book);
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (NumberFormatException ex){
            System.out.println("Please input a valid format.");
        }
        return Optional.empty();
    }

    private void sortBooks() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter how to order the elements: ");
        try {
            String order = bufferRead.readLine();
            Sort.Direction dir = Sort.Direction.DESC;
            System.out.println("Please enter your filters: ");
            String[] r = bufferRead.readLine().split(" ");
            BooksDto b = template.postForObject(sortURL+"/books/"+dir+"",r,BooksDto.class);
            assert b != null;
            if(order.equals("ASC")) {
                List<BookDto> c = new ArrayList<>(b.getBooks());
                Collections.reverse(c);
                c.forEach(System.out::println);
            }
            else
                b.getBooks().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //******************************************************************************************************************
    //PURCHASES
    //TODO check delete
    //******************************************************************************************************************

    private void displayPurchases(){
        PurchasesDto allPurchases = template.getForObject(purchaseURL, PurchasesDto.class);
        assert allPurchases != null;
        allPurchases.getPurchases().forEach(System.out::println);
    }

    private void addPurchase(){
        Optional<Purchase> purchase = readPurchase();
        purchase.ifPresent(c->{
            try {
                PurchaseDto savedPurchase = template.postForObject(purchaseURL,
                        new PurchaseDto(c.getClientID(),c.getBookID(),c.getNrBooks()),
                        PurchaseDto.class);
                System.out.println("Book bought ="+savedPurchase);
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }});
    }

    private void deletePurchase(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            //PurchaseDto purchase = template.getForObject(purchaseURL+"/{id}",id);
            template.delete(purchaseURL + "/{id}", id);
            /*int nr = p.getNrBooks();
            Long idBook = p.getBookID();
            Book b = bookService.findOne(idBook).get();
            Book newBook = new Book(b.getSerialNumber(), b.getTitle(), b.getTitle(), b.getYear(), b.getPrice(), b.getInStock() + nr);
            newBook.setId(b.getId());
            bookService.updateBook(newBook);*/

        } catch (IOException  e) {
            e.printStackTrace();
        }

    }

    private void updatePurchase(){
        Optional<Purchase> purchase = readPurchase();
        purchase.ifPresent(p->{
            template.put(purchaseURL+"/{id}",p,p.getId());
        });
    }

    private Optional<Purchase> readPurchase() {
        System.out.println("Please enter a new purchase: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID: ");
            Long id = Long.parseLong(bufferRead.readLine());
            System.out.println("ID client: ");
            Long idClient = Long.parseLong(bufferRead.readLine());

            System.out.println("ID book: ");
            Long idBook = Long.parseLong(bufferRead.readLine());

            System.out.println("Number of books: ");
            int nrBooks = Integer.parseInt(bufferRead.readLine());

            Purchase purchase = new Purchase(idClient, idBook, nrBooks);
            purchase.setId(id);

            return Optional.of(purchase);
        } catch (IOException  ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    private void filterPurchases() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("See purchases made by the clientID: ");
        try {
            Long filter = Long.parseLong(bufferRead.readLine());
            PurchasesDto filteredPurchase = template.getForObject(purchaseURL+"/filter/"+filter+"",PurchasesDto.class);
            assert filteredPurchase != null;
            filteredPurchase.getPurchases().forEach(System.out::println);
        } catch (IOException  e) {
            e.printStackTrace();
        }

    }
    private void sortPurchases() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter how to order the elements: ");
        try {
            String order = bufferRead.readLine();
            Sort.Direction dir = Sort.Direction.DESC;
            System.out.println("Please enter your filters: ");
            String[] r = bufferRead.readLine().split(" ");
            PurchasesDto b = template.postForObject(sortURL+"/purchases/"+dir+"",r,PurchasesDto.class);
            assert b != null;
            if(order.equals("DESC")) {
                List<PurchaseDto> c = new ArrayList<>(b.getPurchases());
                Collections.reverse(c);
                c.forEach(System.out::println);
            }
            else
                b.getPurchases().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //******************************************************************************************************************

    private void getReport()  {//move in service
        //getting how many books are in stock
        long nrBooksInStock = template.getForObject(bookURL,BooksDto.class).getBooks().stream()
                .map(BookDto::getInStock).reduce(0,(a,b)->a+b);
        System.out.println("Total books in storage : "+nrBooksInStock);

        //getting how many books were sold
        PurchasesDto purchases = template.getForObject(purchaseURL,PurchasesDto.class);
        assert purchases != null;
        long soldBooks = purchases.getPurchases().stream().
                map(PurchaseDto::getNrBooks).count();
        System.out.println("Number of books sold : "+soldBooks);

        //the client that bought from us more often
        //mapping clientID to how many books he/she bought
        Map<Long, Integer> clientIDtoBooksBought = purchases.getPurchases().stream()
                .collect(Collectors.groupingBy(PurchaseDto::getClientID,Collectors.summingInt(PurchaseDto::getNrBooks)));
        //getting the maximum bought books
        clientIDtoBooksBought.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e-> {
                        System.out.println("The "+ template.getForObject(clientURL+"/find/"+e.getKey(),ClientDto.class) + " bought the most books: "+e.getValue());
                });
    }
}
