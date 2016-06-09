<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="/css/custom.css">
    <meta charset="UTF-8">
    <title>Registro</title>
    <script type="text/javascript" src="/js/jquery-2.2.4.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>
<#include "nav.ftl">
</body>
<div class="container">
    <div class="row">
        <div class="col col-md-6 col-md-push-3">
            <div class="well well-lg">
                <div class="row">
                    <div class="col col-md-12">
                    <#if action == "new_article">
                        <h1>Nuevo articulo</h1>
                    <#elseif action == "edit_article">
                        <h1>Edicion de articulo</h1>
                    </#if>
                    </div>
                </div>
                <#if msg??>
                <div class="row">
                    <div class="col col-md-12 text-danger">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <p>${msg}</p>
                        </div>
                    </div>
                </div>
                </#if>
                <div class="row">
                    <div class="col col-md-12">
                        <form
                            <#if
                            action == "new_article">
                            action="/article/new"
                            <#elseif
                            action == "edit_article">
                            action="/article/edit"
                            </#if>
                            method="post">
                            <#if id??>
                            <input type="hidden" name="id" value="${id?string["0"]}">
                            </#if>
                            <#-- inicio form fields -->
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label for="titulo">Titulo</label>
                                    <input type="text" class="form-control" name="titulo" placeholder="Historia generica" <#if titulo??>value="${titulo}"</#if>>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label for="cuerpo">Cuerpo</label>
                                    <textarea class="form-control" name="cuerpo" placeholder="Lorem Ipsum... bla... bla..." rows="9"><#if cuerpo??>${cuerpo}</#if></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label for="etiquetas">Etiquetas</label>
                                    <input type="text" class="form-control" name="etiquetas" placeholder="sql, youtube, google" <#if etiquetas??>value="${etiquetas}"</#if>>
                                </div>
                            </div>
                            <#-- fin form fields -->
                            <div class="row">
                                <div class="col-md-7">
                                    <button type="submit" name="submit" value="submit" class="btn btn-primary btn-lg">
                                        Terminar <#if action == "new_article">Articulo<#elseif action == "edit_article">Edicion de Articulo</#if>
                                    </button>
                                </div>
                            <#if action == "edit_article" && id??>
                            <div class="col-md-3 col-md-offset-2">
                                <a class="btn btn-danger" href="/article/delete/${id?string["0"]}">Borrar</a>
                            </div>
                            </#if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</html>