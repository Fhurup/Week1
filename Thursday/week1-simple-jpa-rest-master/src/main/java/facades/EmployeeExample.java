package facades;

import dto.EmployeeDTO;
import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeExample {

    private static EmployeeExample instance;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EmployeeExample facade = EmployeeExample.getEmployeeExample(emf);

//        EmployeeDTO e1 = facade.addEmployee("John", "Lykkegade 123", 1);
//        EmployeeDTO e2 = facade.addEmployee("Kim", "Lykkegade 132", 2);
//        EmployeeDTO e3 = facade.addEmployee("Svend", "Lykkegade 321", 3);
//        
        facade.getAllEmployees();
    }

    private EmployeeExample() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeExample getEmployeeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeExample();
        }
        return instance;
    }

    public Employee addEmployee(String name, String address, int salary) {
        Employee e = new Employee(name, address, salary);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return e;
        } finally {
            em.close();
        }
    }

    public EmployeeDTO findEmployee(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, id);
            EmployeeDTO DTO = new EmployeeDTO(employee);
            return DTO;
        } finally {
            em.close();
        }
    }
   

    public EmployeeDTO findHighestPaid() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)");
            Employee e = (Employee) query.getSingleResult();
            EmployeeDTO DTO = new EmployeeDTO(e);
            return DTO;
        } finally {
            em.close();
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("Select Employee from Employee employee", Employee.class);
            List<Employee> employees = query.getResultList();
            List<EmployeeDTO> employeesDTO = new ArrayList();
            for(Employee e : employees){
                EmployeeDTO DTO = new EmployeeDTO(e);
                employeesDTO.add(DTO);
                
            }
            return employeesDTO;
        } finally {
            em.close();
        }
    }

 

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
