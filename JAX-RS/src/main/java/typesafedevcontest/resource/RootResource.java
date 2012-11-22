package typesafedevcontest.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Component
@Path("/")
public class RootResource {

    @GET
    public Response index() {
        return Response.temporaryRedirect(URI.create("/logs")).build();
    }

}
