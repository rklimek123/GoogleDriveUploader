package uploader;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import org.apache.camel.component.google.drive.GoogleDriveConfiguration;
import org.apache.camel.component.google.drive.GoogleDriveComponent;

import java.util.concurrent.TimeUnit;

@Component
public class UploaderRoute extends RouteBuilder {

    @Value("${access.token}")
    String accessToken;

    @Value("${client.id}")
    String clientId;

    @Value("${client.secret}")
    String clientSecret;

    @Value("${directory}")
    String directory;

    @Value("${limit.extension}")
    String limit_extension;

    @Override
    public void configure() throws Exception {

        GoogleDriveConfiguration configuration = new GoogleDriveConfiguration();
        configuration.setAccessToken(accessToken);
        configuration.setClientId(clientId);
        configuration.setClientSecret(clientSecret);

        CamelContext context = super.getContext();
        GoogleDriveComponent component = new GoogleDriveComponent(context);
        component.setConfiguration(configuration);
        context.addComponent("google-drive", component);

        from("file://{{directory}}?delete=true")
                .process(new FileMetadataHeaderProcessor())
                .log("Checking if file ${header.filename} has extension " + limit_extension)
                .log("proceed if ${header.extension} == " + limit_extension)
                .filter(simple("${header.extension} == " + limit_extension))
                .to("direct://uploadFile");

        from("direct://uploadFile")
                .process(new FileToGoogleDriveFileProcessor())
                .log("Sending file ${header.CamelGoogleDrive.content}")
                .to("google-drive://drive-files/insert");
    }
}
