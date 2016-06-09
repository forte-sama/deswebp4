package main;

import freemarker.template.Configuration;
import models.Articulo;
import models.Comentario;
import models.Usuario;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import wrappers.*;
import wrappers.db.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {
    public static void main(String[] args) {

        //indicar ruta de archivos publicos.
        staticFileLocation("/public");
        //agregar pantalla de debug. Solo en desarrollo.
        enableDebugScreen();

        //freemarker template engine
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(Main.class, "/templates");

        //probar estado de la base de datos
        DB.test();

        //aplicar filtros
        Filtros.iniciarFiltros();

//        //prueba orm usuarios
//        Usuario us = new Usuario();
//        us.setUsername("xxy");
//        us.setPassword("12345123451234512345123451234512345123451234512345");
//        us.setNombre("PRUEBIN");
//        us.setAdministrador(false);
//        us.setAutor(true);
//        GestorUsuarios.getInstance().editar(us);

//        //prueba orm articulos
//        Articulo ar = new Articulo();
//        ar.setTitulo("si si si ahora prueba ORM si si si");
//        ar.setCuerpo("Aqui tamo en prueba.");
//        ar.setAutor(us);
//        ar.setFecha(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
//        GestorArticulos.getInstance().editar(ar);

//        //prueba orm comentarios
//        Comentario c1 = new Comentario();
//        c1.setComentario("c1");
//        c1.setAutor(us);
//        c1.setArticulo(GestorArticulos.getInstance().find(Long.parseLong("1")));
//        GestorComentarios.getInstance().editar(c1);
//
//        Comentario c2 = new Comentario();
//        c2.setComentario("c2");
//        c2.setAutor(us);
//        c2.setArticulo(GestorArticulos.getInstance().find(Long.parseLong("1")));
//        GestorComentarios.getInstance().editar(c2);
//
//        Comentario c3 = new Comentario();
//        c3.setComentario("c3");
//        c3.setAutor(us);
//        c3.setArticulo(GestorArticulos.getInstance().find(Long.parseLong("2")));
//        GestorComentarios.getInstance().editar(c3);

        System.out.println();
        //Rutas
        get("/", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","index");
            data.put("loggedIn",Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);
            data.put("articulos", GestorArticulos.getInstance().findAll());

            return new ModelAndView(data,"index.ftl");
        }, new FreeMarkerEngine(configuration));

        get("/admin/user/list", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","list_users");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            //obtener los usuarios
            data.put("usuarios", GestorUsuarios.getInstance().findAll());

            return new ModelAndView(data,"user_list.ftl");
        }, new FreeMarkerEngine(configuration));

        get("/admin/user/delete/:username",(request, response) -> {
            String username = request.params("username");

            Usuario target = GestorUsuarios.getInstance().find(username);

            if(target != null) {
                //borrar
                if(GestorUsuarios.getInstance().eliminar(target)) {
                    target = null;
                }
            }
            //redireccionar
            response.redirect("/admin/user/list");

            return "";
        });

        get("/admin/user/edit/:username", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","edit_user");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            String username = request.params("username");
            Usuario target = GestorUsuarios.getInstance().find(username);

            if(target == null) {
                //redireccionar por error
                response.redirect("/admin/user/list");
            }
            else {
                //setear datos para llenar formulario
                data.put("username",target.getUsername());
                data.put("nombre",target.getNombre());

                if(target.isAdministrador()) {
                    data.put("esAdministrador","si");
                }
                else {
                    if (target.isAutor()) {
                        data.put("esAutor", "si");
                    }
                    else {
                        data.put("esLector", "si");
                    }
                }
            }

            return new ModelAndView(data,"register_edit_user.ftl");
        }, new FreeMarkerEngine(configuration));

        post("/admin/user/edit", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","edit_user");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            String username = request.queryParams("username");
            Usuario target = GestorUsuarios.getInstance().find(username.trim());

            if(target == null) {
                //redireccionar por error
                response.redirect("/admin/user/list");
            }
            else {
                //tratar de actualizar usuario
                String password = request.queryParams("password");
                String nombre   = request.queryParams("nombre");
                boolean esAdministrador = request.queryParams("type").contentEquals("administrador");
                boolean esAutor = request.queryParams("type").contentEquals("autor") || esAdministrador;

                //actulizar usuario
                target = new Usuario(username,password,nombre,esAdministrador,esAutor);

                if(GestorUsuarios.getInstance().editar(target)) {
                    //redireccionar
                    response.redirect("/admin/user/list");
                }
                else {
                    //setear datos para llenar formulario
                    data.put("username", target.getUsername());
                    data.put("nombre", target.getNombre());
                    if (target.isAdministrador()) {
                        data.put("esAdministrador", "si");
                    }
                    else {
                        if (target.isAutor()) {
                            data.put("esAutor", "si");
                        } else {
                            data.put("esLector", "si");
                        }
                    }

                    data.put("msg_type", "error");
                    data.put("msg", "Hubo un error con el formulario. Revisa los campos.");
                }
            }

            return new ModelAndView(data,"register_edit_user.ftl");
        }, new FreeMarkerEngine(configuration));


        get("/article/new", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","new_article");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            return new ModelAndView(data,"create_edit_article.ftl");
        }, new FreeMarkerEngine(configuration));

        post("/article/new", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","new_article");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            String raw_etiquetas = request.queryParams("etiquetas");

//            Set<String> etiquetas = _GestorEtiquetas.parsearEtiquetas(raw_etiquetas);

            //Crear articulo en el gestor
            Articulo nuevo = new Articulo();
            nuevo.setTitulo(titulo);
            nuevo.setCuerpo(cuerpo);
            nuevo.setAutor(GestorUsuarios.getInstance().find(Sesion.getUsuarioActivo(request)));
            nuevo.setFecha(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            boolean exito = GestorArticulos.getInstance().crear(nuevo);

            if(exito) {
                //redireccionar a vista con mis articulos
                response.redirect("/");
            }
            else {
                data.put("titulo",titulo);
                data.put("cuerpo",cuerpo);
                data.put("etiquetas",raw_etiquetas);

                data.put("msg_type","error");
                data.put("msg","Hubo un error en el formulario");
            }

            return new ModelAndView(data,"create_edit_article.ftl");
        }, new FreeMarkerEngine(configuration));

        get("/article/edit/:articulo_id", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","edit_article");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            String raw_id = request.params("articulo_id");
            Articulo articulo = null;

            try {
                Long long_id = Long.parseLong(raw_id);
                articulo = GestorArticulos.getInstance().find(long_id);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }

            if (articulo != null) {
                data.put("id",articulo.getId());
                data.put("cuerpo",articulo.getCuerpo());
                data.put("titulo",articulo.getTitulo());
//                data.put("etiquetas",_GestorEtiquetas.cargarEtiquetas(articulo.getId()));
            }
            else {
                response.redirect("/");
            }

            return new ModelAndView(data,"create_edit_article.ftl");
        }, new FreeMarkerEngine(configuration));

        post("/article/edit", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","edit_article");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            //obtener datos del form y del usuario activo
            String raw_id = request.queryParams("id");
            long long_id = -1;
            boolean exito = true;

            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("cuerpo");
            String raw_etiquetas = request.queryParams("etiquetas");

//            Set<String> etiquetas = _GestorEtiquetas.parsearEtiquetas(raw_etiquetas);

            try {
                long_id = Long.parseLong(raw_id.trim());

                Articulo ar = GestorArticulos.getInstance().find(long_id);
                ar.setTitulo(titulo);
                ar.setCuerpo(cuerpo);
//                etiquetas
//                exito = _GestorArticulos.editArticulo(long_id,autor,titulo,cuerpo,etiquetas);
                exito = GestorArticulos.getInstance().editar(ar);
            } catch (NumberFormatException e) {
                //TODO CAMBIAR MENSAJE DE EXITO
                e.printStackTrace();
            }

            if(exito) {
                response.redirect("/");
            }
            else {
                data.put("id",long_id);
                data.put("titulo",titulo);
                data.put("cuerpo",cuerpo);
//                data.put("etiquetas",_GestorEtiquetas.cargarEtiquetas(long_id));

                data.put("msg_type","error");
                data.put("msg","Hubo un error con el formulario.");
            }

            return new ModelAndView(data,"create_edit_article.ftl");
        }, new FreeMarkerEngine(configuration));

        get("/article/delete/:article_id", (request, response) -> {
            String raw_id = request.params("article_id");

            try {
                long long_id = Long.parseLong(raw_id);

                Articulo articulo = GestorArticulos.getInstance().find(long_id);

                if(articulo != null) {
                    GestorArticulos.getInstance().eliminar(articulo);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.redirect("/");

            return "";
        });

        get("/article/view/:article_id", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","view_article");
            data.put("loggedIn",Sesion.isLoggedIn(request));
            data.put("currentUser",Sesion.getUsuarioActivo(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);
            if(esAdmin) {
                data.put("isAdmin","si");
            }

            String raw_article_id = request.params("article_id");
            boolean exito = false;

            try {
                long long_id = Long.parseLong(raw_article_id);

                Articulo articulo = GestorArticulos.getInstance().find(long_id);

                if(articulo != null) {
                    data.put("articulo", articulo);
                    data.put("comentarios",GestorComentarios.getInstance().findByArticle(articulo));
                    exito = true;
                }
            } catch (NumberFormatException e) {
                //TODO CAMBIAR MENSAJE DE EXCEPCION
                e.printStackTrace();
            }

            if(!exito) {
                response.redirect("/");
            }

            return new ModelAndView(data,"view_article.ftl");
        }, new FreeMarkerEngine(configuration));


        post("/comment/new", (request, response) -> {
            if(!Sesion.isLoggedIn(request)) {
                response.redirect("/");
            }

            String username        = Sesion.getUsuarioActivo(request);
            String cuerpo_com      = request.queryParams("comentario");
            String raw_articulo_id = request.queryParams("articulo_id");

            boolean exito = false;

            try {
                long long_articulo_id = Long.parseLong(raw_articulo_id);

                Comentario comentario = new Comentario();
                comentario.setArticulo(GestorArticulos.getInstance().find(long_articulo_id));
                comentario.setComentario(cuerpo_com);
                comentario.setAutor(GestorUsuarios.getInstance().find(username));

                GestorComentarios.getInstance().crear(comentario);
                exito = true;
            } catch (NumberFormatException e) {
                //TODO CAMBIAR MENSAJE DE EXCEPCION
                e.printStackTrace();
            }

            if(exito) {
                response.redirect("/article/view/" + raw_articulo_id);
            }
            else {
                response.redirect("/");
            }

            return "";
        });

        get("/comment/delete/:article_id/:comment_id", (request, response) -> {
            String articulo_id   = request.params("article_id");

            boolean esAdministrador = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            boolean esAutor = Sesion.getTipoUsuarioActivo(request) == "administrador";

            if(esAutor || esAdministrador) {
                String comentario_id = request.params("comment_id");

                try {
                    long long_comentario_id = Long.parseLong(comentario_id);

                    Comentario comentario = GestorComentarios.getInstance().find(long_comentario_id);
                    GestorComentarios.getInstance().eliminar(comentario);
                } catch (NumberFormatException e) {
                    //TODO CAMBIAR MENSAJE DE EXCEPCION
                    e.printStackTrace();
                }
            }

            response.redirect("/article/view/" + articulo_id);

            return "";
        });


        get("/logout",(request, response) -> {
            Sesion.cerrar(request);

            response.redirect("/");

            return "";
        });

        get("/login", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","login");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            return new ModelAndView(data,"login.ftl");
        }, new FreeMarkerEngine(configuration));

        post("/login", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","login");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            if(!request.queryParams("submit").isEmpty()) {
                //obtener datos de quien desea iniciar sesion
                String username = request.queryParams("username");
                String password = request.queryParams("password");

                if(GestorUsuarios.getInstance().credencialesValidas(username,password)) {
                    Usuario user = GestorUsuarios.getInstance().find(username);
                    //iniciar sesion
                    Sesion.iniciar(request,user);

                    //redireccionar con estado de exito
                    response.redirect("/");
                }
                else {
                    //setear datos para llenar formulario
                    data.put("username",username);

                    data.put("msg_type","error");
                    data.put("msg","No se pudo iniciar sesion. Username/password no coinciden.");
                }
            }

            return new ModelAndView(data,"login.ftl");
        }, new FreeMarkerEngine(configuration));

        get("/user/register", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","register");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            return new ModelAndView(data,"register_edit_user.ftl");
        }, new FreeMarkerEngine(configuration));

        post("/user/register", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
            data.put("action","register");
            data.put("loggedIn", Sesion.isLoggedIn(request));
            boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
            data.put("isAutor",Sesion.getTipoUsuarioActivo(request) == "autor" || esAdmin);

            //si el request llego desde el formulario
            if(!request.queryParams("submit").isEmpty()) {
                //obtener datos de nuevo usuario
                String username = request.queryParams("username");
                String password = request.queryParams("password");
                String nombre   = request.queryParams("nombre");
                //no es administrador by default
                boolean esAutor = request.queryParams("type").contentEquals("autor"); //1 : autor, 0 : lector

                //crear nueva instancia
                Usuario newUser = new Usuario(username,password,nombre,false,esAutor);

                //persistir nueva instancia, en caso de ser valida
                if(GestorUsuarios.getInstance().crear(newUser)) {
                    //redireccionar con mensaje de exito
                    response.redirect("/");
                }
                else {
                    //setear datos para llenar formulario
                    data.put("username",newUser.getUsername());
                    data.put("nombre",newUser.getNombre());
                    if(newUser.isAutor()) {
                        data.put("esAutor","si");
                    }
                    else {
                        data.put("esLector","si");
                    }

                    data.put("msg_type","error");
                    data.put("msg","No se pudo crear usuario. Revisar datos del formulario.");
                }
            }

            return new ModelAndView(data,"register_edit_user.ftl");
        }, new FreeMarkerEngine(configuration));
    }
}