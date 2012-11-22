package typesafedevcontest.resource;

import com.sun.jersey.api.view.Viewable;
import jsr166y.ForkJoinPool;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import typesafedevcontest.database.LogDao;
import typesafedevcontest.view.LogsViewModel;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Set;

@Component
@Path("/logs")
public class LogsResource {

    static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    static final Logger log = LoggerFactory.getLogger(LogsResource.class);

    @Resource
    Validator validator;
    @Resource
    LogDao dao;

    @GET
    public Response index() throws Exception {
        LogsViewModel model = new LogsViewModel();
        model.setLogs(dao.findRecent(10));
        return Response.ok(new Viewable("/logs.jsp", model)).build();
    }

    @POST
    public Response create(
            @FormParam("name") String name,
            @FormParam("line") String line) throws Exception {

        CreateLogForm params = new CreateLogForm(name, line);
        Set<ConstraintViolation<CreateLogForm>> violations = validator.validate(params);
        if (!violations.isEmpty()) {
            log.debug("Validation failed : " + violations.size());
            for (ConstraintViolation<CreateLogForm> v : violations) {
                log.debug(v.getPropertyPath().toString() + " " + v.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        dao.create(name, line);

        return Response.ok().build();
    }

    @POST
    @Path("/async")
    public Response createAsynchronously(
            @FormParam("name") final String name,
            @FormParam("line") final String line) throws Exception {

        CreateLogForm params = new CreateLogForm(name, line);
        Set<ConstraintViolation<CreateLogForm>> violations = validator.validate(params);
        if (!violations.isEmpty()) {
            log.debug("Validation failed : " + violations.size());
            for (ConstraintViolation<CreateLogForm> v : violations) {
                log.debug(v.getPropertyPath().toString() + " " + v.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        FORK_JOIN_POOL.execute(new Runnable() {
            public void run() {
                dao.create(name, line);
            }
        });

        return Response.status(Response.Status.ACCEPTED).build();
    }

    public static class CreateLogForm {
        public CreateLogForm(String name, String line) {
            this.name = name;
            this.line = line;
        }

        @NotEmpty
        public String name;
        @NotEmpty
        public String line;
    }

}
