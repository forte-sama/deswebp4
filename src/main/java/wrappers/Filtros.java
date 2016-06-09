package wrappers;

import models.Articulo;
import models.Usuario;
import spark.Response;

import static spark.Spark.before;
import spark.Request;
import wrappers.db._GestorArticulos;
import wrappers.db._GestorUsuarios;

/**
 * Created by forte on 01/06/16.
 */
public class Filtros {
    public static void iniciarFiltros() {
//        before("/user/register",(request, response) -> {
//            //si la sesion esta activa, redireccionar
//            if(Sesion.isLoggedIn(request)) {
//                response.redirect("/");
//            }
//        });
//        before("/admin/user/edit/:username",(request, response) -> {
//            validarAccesoUsuario(request,response,"get");
//        });
//        before("/admin/user/edit",(request, response) -> {
//            validarAccesoUsuario(request,response,"post");
//        });
//        before("/admin/user/delete/:username",(request, response) -> {
//            validarAccesoUsuario(request,response,"get");
//        });
//        before("/admin/user/list",(request, response) -> {
//            if(!Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null)) {
//                response.redirect("/");
//            }
//        });
//
//        before("/article/new", (request, response) -> {
//            //si no ha iniciado sesion, redireccionar
//            boolean esAutor = Sesion.accesoValido(AccessTypes.AUTHOR_ONLY,request,null);
//            boolean esAdministrador = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
//
//            if(!esAutor && !esAdministrador) {
//                response.redirect("/");
//            }
//        });
//        before("/article/edit/:article_id", (request, response) -> {
//            //validar por metodo GET el acceso editar el articulo con id=article_id
//            validarAccesoArticulo(request,response,"get");
//        });
//        before("/article/edit", (request, response) -> {
//            //validar por metodo POST el acceso editar el articulo con id=article_id
//            validarAccesoArticulo(request,response,"post");
//        });
//        before("/article/delete/:article_id", (request, response) -> {
//            //validar por metodo POST el acceso a borrar el articulo con id=article_id
//            boolean exito = false;
//
//            try {
//                long id = Long.parseLong(request.params("article_id"));
//
//                Articulo articulo = _GestorArticulos.getArticulo(id);
//
//                //si el articulo con article_id es del usuario que ha iniciado sesion
//                boolean esElAutor = Sesion.accesoValido(AccessTypes.OWNER_ONLY,request,articulo);
//                boolean esAdministrador = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
//
//                if(!(esElAutor || esAdministrador)) {
//                    response.redirect("/");
//                }
//            } catch (NumberFormatException e) {
//                exito = false;
//            }
//
//            if(!exito) {
//                response.redirect("/");
//            }
//        });
//
//        before("/comment/new", (request, response) -> {
//            if(!Sesion.isLoggedIn(request)) {
//                response.redirect("/");
//            }
//        });
//
//        before("/comment/delete/:article_id/:comment_id", (request, response) -> {
//            String articulo_id   = request.params("article_id");
//
//            boolean exito = false;
//
//            try {
//                long long_articulo   = Long.parseLong(articulo_id);
//
//                Articulo articulo = _GestorArticulos.getArticulo(long_articulo);
//
//                exito = articulo.getAutorId() == Sesion.getUsuarioActivo(request);
//
//            } catch (NumberFormatException e) {
//                //TODO CAMBIAR MENSAJE DE EXCEPCION
//                exito = false;
//                e.printStackTrace();
//            }
//
//            if(!exito) {
//                response.redirect("/article/view/" + articulo_id);
//            }
//        });
    }
//
//    private static void validarAccesoArticulo(Request request, Response response, String metodo) {
//        boolean exito = true;
//
//        String raw_id = metodo == "get" ? request.params("article_id") : request.queryParams("id");
//
//        try {
//            long id = Long.parseLong(raw_id);
//
//            Articulo articulo = _GestorArticulos.getArticulo(id);
//
//            //si el articulo con article_id es del usuario que ha iniciado sesion
//            exito = Sesion.accesoValido(AccessTypes.OWNER_ONLY,request,articulo);
//        } catch (NumberFormatException e) {
//            exito = false;
//        }
//
//        //redireccionar si no cumplio con nada
//        if(!exito) {
//            response.redirect("/");
//        }
//    }
//
//    private static void validarAccesoUsuario(Request request, Response response, String metodo) {
//
//        String username = metodo == "get" ? request.params("username") : request.queryParams("username");
//
//        Usuario user = _GestorUsuarios.getUsuario(username);
//
//        boolean esAdmin = Sesion.accesoValido(AccessTypes.ADMIN_ONLY,request,null);
//        boolean esUsuarioActivo = Sesion.accesoValido(AccessTypes.OWNER_ONLY,request,user);
//
//        //si no es (un admin o el mismo usuario), redireccionar
//        if(!(esAdmin || esUsuarioActivo)) {
//            response.redirect("/");
//        }
//    }
}
