package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.EmployeeDTO;
import entities.Employee;
import facades.EmployeeExample;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
public class EmployeeResource {

    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    EmployeeExample facade = EmployeeExample.getEmployeeExample(emf);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson2() {
        List<EmployeeDTO> all = facade.getAllEmployees();
        String jsonString = GSON.toJson(all);
        return jsonString;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }

    @Path("highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson3() {
        EmployeeDTO e = facade.findHighestPaid();
        String jsonString = GSON.toJson(e);
        return jsonString;
    }


    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public String findByID(Employee entity, @PathParam("id") int id) {
        EmployeeDTO e = facade.findEmployee(id);
        String jsonString = GSON.toJson(e);
        return jsonString;
    }
}
