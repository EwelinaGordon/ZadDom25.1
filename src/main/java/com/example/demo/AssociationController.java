package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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


    @GetMapping("/edytujBudynek")
    public String edytujBudynek(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building building = entityManager.find(Building.class, id);
        model.addAttribute(building);
        entityManager.close();
        return "EditBuilding";
    }

    @PostMapping("/zapiszBudynek")
    public String edytujBudynek(Building building) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building buildingFromDatabase = entityManager.find(Building.class, building.getId());
        entityManager.getTransaction().begin();
        buildingFromDatabase.setAddress(building.getAddress());
        buildingFromDatabase.setName(building.getName());
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:budynki";
    }

    @GetMapping("/usunBudynek")
    public String usunBudynek(@RequestParam Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building building = entityManager.find(Building.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(building);
        entityManager.getTransaction().commit();
        return "redirect:budynki";
    }

    @GetMapping("/szczegolyBudynku")
    public String szczegolyBudynku(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building building = entityManager.find(Building.class, id);
        List<Flat> flats = building.getFlat();
        double area = 0;
        List<Inhabitant> inhabitants = new ArrayList<>();
        for (Flat flat : flats) {
            area += flat.getSurface();
            inhabitants.addAll(flat.getInhabitant());
        }
        model.addAttribute("flatsArea", area);
        model.addAttribute("inhabitants", inhabitants);
        model.addAttribute("allFlats", flats);
        return "BuildingDetails";
    }

    @GetMapping("/szczegolyMieszkania")
    public String szczegolyMieszkania(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Flat flat = entityManager.find(Flat.class, id);
        model.addAttribute("inhabitants", flat.getInhabitant());
        model.addAttribute("flatNumber", flat.getNumber());
        return "FlatDetails";
    }

    @GetMapping("/edytujMieszkanie")
    public String edytujMieszkanie(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Flat flat = entityManager.find(Flat.class, id);
        model.addAttribute(flat);
        entityManager.close();
        return "EditFlat";
    }

    @PostMapping("/zapiszMieszkanie")
    public String edytujMieszkanie(Flat flat) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Flat flatFromDatabase = entityManager.find(Flat.class, flat.getId());
        entityManager.getTransaction().begin();
        flatFromDatabase.setNumber(flat.getNumber());
        flatFromDatabase.setSurface(flat.getSurface());
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:mieszkania";
    }

    @GetMapping("/usunMieszkanie")
    public String usunMieszkanie(@RequestParam Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Flat flat = entityManager.find(Flat.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(flat);
        entityManager.getTransaction().commit();
        return "redirect:mieszkania";
    }


    @GetMapping("/edytujMieszkanca")
    public String edytujMieszkanca(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Inhabitant flat = entityManager.find(Inhabitant.class, id);
        model.addAttribute(flat);
        entityManager.close();
        return "EditInhabitant";
    }

    @PostMapping("/zapiszMieszkanca")
    public String edytujMieszkanca(Inhabitant inhabitant) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Inhabitant inhibitantFromDatabase = entityManager.find(Inhabitant.class, inhabitant.getId());
        entityManager.getTransaction().begin();
        inhibitantFromDatabase.setFirstName(inhabitant.getFirstName());
        inhibitantFromDatabase.setLastName(inhabitant.getLastName());
        inhibitantFromDatabase.setGender(inhabitant.getGender());
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:mieszkancy";
    }

    @GetMapping("/usunMieszkanca")
    public String usunMieszkanca(@RequestParam Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Inhabitant flat = entityManager.find(Inhabitant.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(flat);
        entityManager.getTransaction().commit();
        return "redirect:mieszkancy";
    }

    @GetMapping("/dodajMieszkanie")
    public String dodajMieszkanieDoWspolnoty(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building building = entityManager.find(Building.class, id);
        TypedQuery<Flat> allFlats = entityManager.createQuery("select f from Flat f", Flat.class);
        List<Flat> flats = allFlats.getResultList();
        model.addAttribute("buildingId", id);
        model.addAttribute("address", building.getAddress());
        model.addAttribute("flats", flats);
        entityManager.close();
        return "AddFlatToBuilding";
    }

    @PostMapping("/dodajMieszkanie")
    public String dodajMieszkanieDoWspolnoty(@RequestParam Long id, @RequestParam Long flatId, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Building building = entityManager.find(Building.class, id);
        Flat flat = entityManager.find(Flat.class, flatId);
        List<Flat> flatsForBuilding = building.getFlat();
        entityManager.getTransaction().begin();
        flatsForBuilding.add(flat);
        building.setFlat(flatsForBuilding);
        flat.setBuilding(building);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:/budynki";
    }



    @GetMapping("/dodajMieszkancaDoMieszkania")
    public String dodajMieszkancaDoMieszkania(@RequestParam Long id, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Inhabitant> allInhabitants = entityManager.createQuery("select i from Inhabitant i", Inhabitant.class);
        List<Inhabitant> inhabitants = allInhabitants.getResultList();
        model.addAttribute("flatId", id);
        model.addAttribute("inhabitants", inhabitants);
        entityManager.close();
        return "AddInhabitantToFlat";
    }


    @PostMapping("/dodajMieszkancaDoMieszkania")
    public String dodajMieszkancaDoMieszkania(@RequestParam Long id, @RequestParam Long inhabitantId, Model model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Flat flat = entityManager.find(Flat.class, id);
        Inhabitant inhabitant = entityManager.find(Inhabitant.class, inhabitantId);
        List<Inhabitant> inhabitants = flat.getInhabitant();
        entityManager.getTransaction().begin();
        inhabitants.add(inhabitant);
        flat.setInhabitant(inhabitants);
        inhabitant.setFlat(flat);
        entityManager.getTransaction().commit();
        entityManager.close();
        return "redirect:/mieszkania";
    }
}
