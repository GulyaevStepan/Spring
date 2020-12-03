package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class Main {

    private static EntityManagerFactory emFactory;
    private static EntityManager em;

    public static void main(String[] args) {

        emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        em = emFactory.createEntityManager();

        Person person = new Person("Ivan", "Ivanov");
        Product product = new Product("Knife");
        //delete(new Product(), 2);
        //insert(product);
        //System.out.println(select(new Person(), 1));
        updateManyToMany(person, 1, product, 6);
        System.out.println(selectProductsFromPerson(new Person(), 1));
        em.close();

    }


    private static void insert(Object object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    private static Person select(Person person, int id) {
        person = em.find(person.getClass(), id);
        return person;
    }

    private static Product select(Product product, int id) {
        product = em.find(product.getClass(), id);
        return product;
    }

    private static void update(Person person, int id) {
        Person pers = select(person, id);
        em.getTransaction().begin();
        pers.setFirstname(person.getFirstname());
        pers.setLastname(person.getLastname());
        em.getTransaction().commit();
    }

    private static void update(Product product, int id) {
        Product prod = select(product, id);
        em.getTransaction().begin();
        prod.setTitle(product.getTitle());
        em.getTransaction().commit();
    }

    private static void delete(Person person, int id) {
        person = select(person, id);
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
    }

    private static void delete(Product product, int id) {
        product = select(product, id);
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }

    private static void updateManyToMany(Person person, int idPers, Product product, int idProd) {
        person = select(person, idPers);
        product = select(product, idProd);
        em.getTransaction().begin();
        List<Product> products = person.getProducts();
        products.add(product);
        person.setProducts(products);
        em.getTransaction().commit();
    }

    private static void updateManyToMany(Product product, int idProd, Person person, int idPers) {
        person = select(person, idPers);
        product = select(product, idProd);
        em.getTransaction().begin();
        List<Person> persons = product.getPersons();
        persons.add(person);
        product.setPersons(persons);
        em.getTransaction().commit();
    }

    private static List<Product> selectProductsFromPerson(Person person, int id) {
        person = em.find(person.getClass(), id);
        List<Product> products = person.getProducts();
        return products;
    }

    private static List<Person> selectPersonsFromProduct(Product product, int id) {
        product = em.find(product.getClass(), id);
        List<Person> persons = product.getPersons();
        return persons;
    }


}
