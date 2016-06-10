<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="/css/custom.css">
    <meta charset="UTF-8">
    <title>Inicio</title>
    <script type="text/javascript" src="/js/jquery-2.2.4.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>
<#include "nav.ftl">
</body>
<div class="container">
    <div class="row">
        <div class="col col-md-12">
            <div class="well well-lg">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h1>${articulo.getTitulo()}</h1>
                        <hr />
                        <h4>Escrita por: <strong>${articulo.getAutor().getUsername()}</strong></h4>
                        <#if currentUser??>
                            <#if currentUser == articulo.getAutor().getUsername() || isAdmin??>
                            <a href="/article/edit/${articulo.getId()}">
                                <span class="glyphicon glyphicon-pencil"></span> Editar
                            </a>
                            </#if>
                        </#if>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col col-md-8">
                            ${articulo.getCuerpo()}
                            </div>
                            <div class="col col-md-4">
                                <#list articulo.etiquetas() as etiqueta>
                                <a class="label label-danger" href="/tag/${etiqueta.getEtiqueta()}/page/1">
                                    ${etiqueta.getEtiqueta()}
                                </a> &nbsp;
                                </#list>
                                <hr />
                                <#if num_likes??>
                                <p>Cantidad de likes: <strong>${num_likes}</strong></p>
                                </#if>
                                <hr />
                                <#if loggedIn?? && loggedIn == true>
                                <div class="alert alert-warning">
                                    <form action="/comment/new" method="post">
                                        <input type="hidden" name="articulo_id" value="${articulo.getId()}">
                                        <div class="row">
                                            <div class="form-group col-md-12">
                                                <label for="comentario">Nuevo Comentario</label>
                                                <input type="text" class="form-control" name="comentario" placeholder="Me gusto este articulo">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="form-group col-md-12">
                                                <strong>Me gusto el articulo </strong>
                                                <input type="checkbox" class="form-control" name="voto" value="bueno">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-7">
                                                <button type="submit" name="submit" value="submit" class="btn btn-primary btn-sm">
                                                    Comentar
                                                </button>
                                            </div>
                                            <#if action == "edit_user" && username??>
                                            <div class="col-md-3 col-md-offset-2">
                                                <a class="btn btn-danger" href="/admin/user/delete/${username}">Borrar usuario</a>
                                            </div>
                                            </#if>
                                        </div>
                                    </form>
                                </div>
                                <hr />
                                </#if>
                                <h4>Comentarios</h4>
                                <#list comentarios as comentario>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <#if comentario.isVoto() == true>
                                        <span class="label label-success">
                                            <span class="glyphicon glyphicon-thumbs-up"></span>
                                        </span>
                                        <#else>
                                            <span class="label label-danger">
                                            <span class="glyphicon glyphicon-thumbs-down"></span>
                                        </span>
                                        </#if>
                                        <span class="label label-default">
                                            ${comentario.countLikes()}
                                        </span>
                                        <#if  loggedIn?? && loggedIn == true>
                                        &nbsp;
                                        <a href="/comment/like/${comentario.getId()}/${articulo.getId()}" class="btn btn-default btn-sm">
                                            Like
                                        </a>
                                        </#if>
                                        &nbsp;
                                        ${comentario.getAutor().getUsername()} dijo:
                                    </div>
                                    <div class="panel-body">
                                    ${comentario.getComentario()}
                                    </div>
                                    <#if currentUser?? && currentUser == articulo.getAutor().getUsername() || isAdmin??>
                                        <div class="panel-footer">
                                            <a  class="label label-default" href="/comment/delete/${articulo.getId()}/${comentario.getId()}">
                                                <span class="glyphicon glyphicon-trash"></span>
                                                Borrar
                                            </a>
                                        </div>
                                    </#if>
                                </div>
                                </#list>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</html>