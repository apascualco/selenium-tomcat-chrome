package apascualco.blog.selenium;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

class TomcatEmbedded {

    static Tomcat server;

    static void levantarShowcase() throws FileNotFoundException, LifecycleException {
        String contextoAplicacion = "showcase-5.3";
        String fileName = contextoAplicacion + ".war";
        URL url = TomcatEmbedded.class.getClassLoader().getResource(fileName);
        if(url == null) {
            throw new FileNotFoundException("No se ha encontrado ell archivo " + fileName);
        }
        String fileAbsolutePath = url.getFile();
        if(fileAbsolutePath == null || fileAbsolutePath.isEmpty()) {
            throw new FileNotFoundException("No se ha encontrado ell archivo " + fileName);
        }
        File desplegable = new File(fileAbsolutePath);
        if(!desplegable.exists() || !desplegable.isFile()) {
            throw new FileNotFoundException();
        }
        String directorioDeTrabajo = desplegable.getParent();
        System.out.println("Iniciando servidor");
        server = TomcatEmbedded.iniciarTomcat(contextoAplicacion, directorioDeTrabajo, desplegable);
        server.start();
    }

    private static Tomcat iniciarTomcat(String contextoAplicacion, String directorioDeTrabajo, File desplegable) {
        System.out.println("Directorio de trabajo : " + directorioDeTrabajo);
        System.out.println("Creando servidor");
        Tomcat tomcatEmbedded = new Tomcat();
        TomcatEmbedded.configuracionBaseToncat(tomcatEmbedded, directorioDeTrabajo);

        System.out.println("Añadiendo aplicacion");
        tomcatEmbedded.addWebapp(tomcatEmbedded.getHost(), "/".concat(contextoAplicacion), desplegable.getAbsolutePath());
        TomcatEmbedded.agregarUsuariosYRoles(tomcatEmbedded);
        return tomcatEmbedded;
    }

    private static void configuracionBaseToncat(Tomcat tomcatEmbedded, String directorioDeTrabajo) {
        tomcatEmbedded.setPort(8080);
        tomcatEmbedded.setBaseDir(directorioDeTrabajo);
        tomcatEmbedded.getHost().setAppBase(directorioDeTrabajo);
        tomcatEmbedded.getHost().setAutoDeploy(true);
        tomcatEmbedded.getHost().setDeployOnStartup(true);
    }

    private static void agregarUsuariosYRoles(Tomcat tomcatEmbedded){
        System.out.println("Usuarios y roles");
        tomcatEmbedded.addUser("admin", "admin");
        tomcatEmbedded.addUser("user", "user");
        tomcatEmbedded.addRole("admin", "admin");
        tomcatEmbedded.addRole("admin", "user");
        tomcatEmbedded.addRole("user", "user");
    }
}
