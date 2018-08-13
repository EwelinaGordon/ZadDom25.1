package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
public class AssociationController {

    @PersistenceUnit
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("building", new Building());
        model.addAttribute("flat", new Flat());
        model.addAttribute("inhabitant", new Inhabitant());
        return "home";
    }

    @PostMapping("/addbuilding")
    public String dodajBudynek(Building building) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(building);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:/";
    }


    @PostMapping("/addflat")
    public String dodajMieszkanie(Flat flat) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(flat);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:/";
    }

    @GetMapping("/budynki")
    public String pokazBudynki(Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Building> query = entityManager.createQuery("select b from Building b", Building.class);
        List<Building> resultList = query.getResultList();
        model.addAttribute("buildings", resultList);
        entityManager.close();
        return "buildings";
    }

    @GetMapping("/mieszkania")
    public String pokazMieszkania(Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Flat> query = entityManager.createQuery("select f from Flat f", Flat.class);
        List<Flat> resultList = query.getResultList();
        model.addAttribute("flats", resultList);
        entityManager.close();
        return "flats";
    }

    @GetMapping("/mieszkancy")
    public String pokazMieszkancow(Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Inhabitant> query = entityManager.createQuery("select i from Inhabitant i", Inhabitant.class);
        List<Inhabitant> resultList = query.getResultList();
        model.addAttribute("inhabitants", resultList);
        entityManager.close();
        return "inhabitants";
    }

    @PostMapping("/addinhabitant")
    public String dodajMieszkanca(Inhabitant inhabitant) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(inhabitant);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:/";
    }

}
