package com.jorgehrique;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import com.jorgehrique.enums.ApiAnnotationsType;
import com.jorgehrique.enums.DocumentationSpec;
import com.jorgehrique.services.IDocBuilder;
import com.jorgehrique.services.IMapper;
import com.jorgehrique.services.impl.SpringWebDocBuilder;
import com.jorgehrique.services.impl.SpringWebMapper;

@Mojo(name = "doc-generator", defaultPhase = LifecyclePhase.PACKAGE)
public class ApiDocGenMojo extends AbstractMojo {

    private IMapper mapper;
    private IDocBuilder docBuilder;

    @Parameter(name = "apiAnnotationsType", defaultValue = "SPRING_WEB")
    private ApiAnnotationsType apiAnnotationsType;

    @Parameter(name = "documentationSpec", defaultValue = "OpenApi3")
    private DocumentationSpec documentationSpec;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        mapper = switch (apiAnnotationsType) {
            case SPRING_WEB -> new SpringWebMapper();
            case JAX_RS -> throw new UnsupportedOperationException(
                    "JAX_RS support not implemented yet!");
        };

        docBuilder = switch (documentationSpec) {
            case OPEN_API_3 -> new SpringWebDocBuilder();
            case OPEN_API_2 -> throw new UnsupportedOperationException(
                    "OpenAPI v2 support not implemented yet!");
        };

        // Extract paths and verbs

        // generate json

        getLog().info("apiAnnotationsType: " + apiAnnotationsType.toString());
        getLog().info("ddocumentationSpec: " + documentationSpec.toString());
    }

    public void setApiAnnotattionsType(ApiAnnotationsType annotationsType) {
        this.apiAnnotationsType = annotationsType;
    }

    public void setDocumentationSpec(DocumentationSpec documentationSpec) {
        this.documentationSpec = documentationSpec;
    }

}
